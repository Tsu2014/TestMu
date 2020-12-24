package com.tsu.annotation_bk_compiler;

import com.google.auto.service.AutoService;
import com.tsu.annotation_bk.TSUBindView;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

@AutoService(Processor.class)
public class BKAnnotationCompiler extends AbstractProcessor {

    Filer filer;
    Messager messager;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        filer = processingEnvironment.getFiler();
        messager = processingEnvironment.getMessager();
        messager.printMessage(Diagnostic.Kind.NOTE , "init Annotation message !");
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> types = new HashSet<String>();
        types.add(TSUBindView.class.getCanonicalName());
        return types;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return processingEnv.getSourceVersion();
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        Set<? extends Element> elementsAnnoationWith = roundEnvironment.getElementsAnnotatedWith(TSUBindView.class);
        //TypeElement class\ VariableElement variable\ ExecutableElement function\ PackageElement package
        Map<String , List<VariableElement>> map = new HashMap<>();
        for(Element element : elementsAnnoationWith){
            VariableElement variableElement = (VariableElement)element;
            //get this variable's class
            TypeElement typeElement = (TypeElement) variableElement.getEnclosingElement();
            String activityName = typeElement.getSimpleName().toString();
            List<VariableElement> variableElements = map.get(activityName);
            if(variableElements == null){
                variableElements = new ArrayList<>();
                map.put(activityName , variableElements);
            }
            variableElements.add(variableElement);
        }

        if(map.size()>0){
            Writer writer = null;
            Iterator<String> iterator = map.keySet().iterator();
            while(iterator.hasNext()){
                String activityName = iterator.next();
                List<VariableElement> variableElements = map.get(activityName);
                String packageName = getPackageName(variableElements.get(0));
                String newName = activityName+"$$ViewBinder";
                //create Java file
                try {
                    JavaFileObject sourceFile = filer.createSourceFile(packageName+"."+newName);
                    writer = sourceFile.openWriter();
                    StringBuffer stringBuffer = new StringBuffer();
                    stringBuffer.append("package "+packageName+";\n");
                    stringBuffer.append("import com.tsu.testmu.common.ITSUButterKnifer;\n");
                    stringBuffer.append("import android.view.View;\n\n");
                    stringBuffer.append("public class "+newName+" implements ITSUButterKnifer<"+packageName+"."+activityName+"> {\n");
                    stringBuffer.append("    public void bind("+packageName+"."+activityName+" target){\n");
                    for(VariableElement variableElement : variableElements){
                        String variableName = variableElement.getSimpleName().toString();
                        int resId = variableElement.getAnnotation(TSUBindView.class).value();
                        stringBuffer.append("        target."+variableName+"=target.findViewById("+resId+");\n");
                    }
                    stringBuffer.append("    }\n}\n");
                    writer.write(stringBuffer.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }finally {
                    if(writer!=null){
                        try {
                            writer.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        return false;
    }

    public String getPackageName(VariableElement variableElement){
        TypeElement typeElement = (TypeElement)variableElement.getEnclosingElement();
        PackageElement packageOf = processingEnv.getElementUtils().getPackageOf(typeElement);
        return packageOf.getQualifiedName().toString();
    }
}

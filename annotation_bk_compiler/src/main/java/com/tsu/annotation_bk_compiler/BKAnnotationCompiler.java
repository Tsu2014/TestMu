package com.tsu.annotation_bk_compiler;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;
import com.tsu.annotation_bk.ITSUButterKnifer;
import com.tsu.annotation_bk.TSUBindView;
import com.tsu.annotation_bk.TSUOnClick;

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
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
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

    private void processTSUBindView(Set<? extends Element> elementsBindView){
        Map<String , List<VariableElement>> map = new HashMap<>();
        for(Element element : elementsBindView){
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

            Iterator<String> iterator = map.keySet().iterator();
            while(iterator.hasNext()){
                String activityName = iterator.next();
                List<VariableElement> variableElements = map.get(activityName);
                String packageName = getPackageName(variableElements.get(0));
                String newName = activityName+"$$ViewBinder";
                createJavaFile(activityName , packageName , newName , variableElements);
            }
        }
    }

    private void createJavaFileByPoet(String activityName , String pkgName , String className , List<VariableElement> variableElements){
        try {
            MethodSpec.Builder bindBuilder = MethodSpec.methodBuilder("bind")
                    .addModifiers(Modifier.PUBLIC)
                    .returns(void.class)
                    .addParameter(Class.forName(pkgName+"."+activityName).getComponentType() , "target");

            for(VariableElement variableElement : variableElements){
                String variableName = variableElement.getSimpleName().toString();
                int resId = variableElement.getAnnotation(TSUBindView.class).value();
                //stringBuffer.append("        target."+variableName+"=target.findViewById("+resId+");\n");
                bindBuilder.addStatement("target.$S=target.findViewById($S)",variableName,""+resId);
            }
            MethodSpec bind = bindBuilder.build();

            TypeSpec classSpec = TypeSpec.classBuilder(className)
                    .addModifiers(Modifier.PUBLIC)
                    .addSuperinterface(ITSUButterKnifer.class).build();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void createJavaFile(String activityName , String pkgName , String className , List<VariableElement> variableElements){
        //create Java file
        Writer writer = null;
        try {
            JavaFileObject sourceFile = filer.createSourceFile(pkgName+"."+className);
            writer = sourceFile.openWriter();
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("package "+pkgName+";\n");
            stringBuffer.append("import com.tsu.annotation_bk.ITSUButterKnifer;\n");
            stringBuffer.append("import android.view.View;\n\n");
            stringBuffer.append("public class "+className+" implements ITSUButterKnifer<"+pkgName+"."+activityName+"> {\n");
            stringBuffer.append("    public void bind("+pkgName+"."+activityName+" target){\n");
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

    private void processTSUOnClick(Set<? extends Element> elementsOnClick){
        Map<String , List<ExecutableElement>> map = new HashMap<>();
        for(Element element : elementsOnClick){
            ExecutableElement executableElement = (ExecutableElement)element;
            //get this variable's class
            TypeElement typeElement = (TypeElement) executableElement.getEnclosingElement();
            String activityName = typeElement.getSimpleName().toString();
            List<ExecutableElement> executableElements = map.get(activityName);
            if(executableElements == null){
                executableElements = new ArrayList<>();
                map.put(activityName , executableElements);
            }
            executableElements.add(executableElement);
        }

        if(map.size()>0){
            Writer writer = null;
            Iterator<String> iterator = map.keySet().iterator();
            while(iterator.hasNext()){
                String activityName = iterator.next();
                List<ExecutableElement> executableElements = map.get(activityName);
                String packageName = getPackageName(executableElements.get(0));
                String newName = activityName+"$$OnClick";
                //create Java file
                try {
                    JavaFileObject sourceFile = filer.createSourceFile(packageName+"."+newName);
                    writer = sourceFile.openWriter();
                    StringBuffer stringBuffer = new StringBuffer();
                    stringBuffer.append("package "+packageName+";\n");
                    stringBuffer.append("import com.tsu.annotation_bk.ITSUButterKnifer1;\n");
                    stringBuffer.append("import android.view.View;\n\n");
                    stringBuffer.append("public class "+newName+" implements ITSUButterKnifer1<"+packageName+"."+activityName+"> {\n");
                    stringBuffer.append("\tpublic void setListener(final "+packageName+"."+activityName+" target){\n");
                    for(ExecutableElement executableElement : executableElements) {
                        String methodName = executableElement.getSimpleName().toString();
                        int resId = executableElement.getAnnotation(TSUOnClick.class).value();
                        stringBuffer.append("\t\ttarget.findViewById(" + resId + ").setOnClickListener(new View.OnClickListener(){ \n\t\t\t@Override\n\t\t\tpublic void onClick(View v) {\n\t\t\t\ttarget." + methodName + "();\n\t\t\t}\n\t\t});\n");
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
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        Set<? extends Element> elementsBindView = roundEnvironment.getElementsAnnotatedWith(TSUBindView.class);
        //TypeElement class\ VariableElement variable\ ExecutableElement function\ PackageElement package
        processTSUBindView(elementsBindView);
        Set<? extends Element> elementsOnClick = roundEnvironment.getElementsAnnotatedWith(TSUOnClick.class);
        processTSUOnClick(elementsOnClick);
        return false;
    }

    public String getPackageName(Element variableElement){
        TypeElement typeElement = (TypeElement)variableElement.getEnclosingElement();
        PackageElement packageOf = processingEnv.getElementUtils().getPackageOf(typeElement);
        return packageOf.getQualifiedName().toString();
    }


}

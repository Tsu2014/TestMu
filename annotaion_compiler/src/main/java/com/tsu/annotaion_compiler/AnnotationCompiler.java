package com.tsu.annotaion_compiler;

import com.google.auto.service.AutoService;
import com.tsu.annotation.BindPath;

import java.io.Writer;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.JavaFileObject;

@AutoService(Processor.class) //标记这是注解处理器
public class AnnotationCompiler extends AbstractProcessor {

    Filer filer;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        filer = processingEnvironment.getFiler();
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return processingEnv.getSourceVersion();
    }

    /**
     * 生命这个注解处理器要识别的注解
     * @return
     */
    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> types = new HashSet<>();
        types.add(BindPath.class.getCanonicalName());
        return types;
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        Set<? extends Element> elementsAnnotatedWith = roundEnvironment.getElementsAnnotatedWith(BindPath.class);
        Map<String ,String> map = new HashMap<String ,String >();
        for(Element element : elementsAnnotatedWith){
            TypeElement typeElement = (TypeElement)element;
            String activityName = typeElement.getQualifiedName().toString();
            String key = typeElement.getAnnotation(BindPath.class).value();
            map.put(key , activityName+".class");
        }

        if(map.size()>0){
            String className = "ActivityUtil"+System.currentTimeMillis();
            //可以用JavaPoe
            Writer writer = null;
            try{
                JavaFileObject sourceFile = filer.createSourceFile("com.tsu.util."+className);
                writer = sourceFile.openWriter();
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("package com.tsu.util;\n");
                stringBuffer.append("import com.tsu.router.SRouter;\n");
                stringBuffer.append("import com.tsu.router.IRouter;\n"+"\n"+"public class "+className+" implements IRouter {\n"+
                        "    @override\n"+
                        "    public void putActivity(){\n");

                Iterator<String> iterator = map.keySet().iterator();
                while(iterator.hasNext()){
                    String key = iterator.next();
                    String value = map.get(key);
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }

        return false;
    }
}

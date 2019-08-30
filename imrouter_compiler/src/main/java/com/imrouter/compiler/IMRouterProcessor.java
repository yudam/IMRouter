package com.imrouter.compiler;

import com.imrouter.annotation.Router;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;

/**
 * User: maodayu
 * Date: 2019/8/29
 * Time: 21:01
 */
public class IMRouterProcessor extends AbstractProcessor {

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
    }


    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return Collections.singleton(Router.class.getCanonicalName());
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(Router.class);
        String packages = processingEnv.getOptions().get("IMROUTER");
        looger(packages);
        if (elements != null && elements.size() > 0) {
            for (Element element : elements) {
                if (element.getKind() == ElementKind.CLASS) {

                    TypeElement typeElement = (TypeElement) element;

                    //获取注解相关信息
                    Router router = element.getAnnotation(Router.class);

                    looger(router.path());

                    looger(element.getSimpleName().toString());
                    looger(typeElement.getQualifiedName().toString());

                    TypeMirror typeMirror = element.asType();
                }
            }

            return true;
        }
        return false;
    }


    public void looger(String msg) {

        System.out.println("------" + msg);
    }
}

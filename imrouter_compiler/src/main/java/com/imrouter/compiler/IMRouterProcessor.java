package com.imrouter.compiler;

import com.imrouter.annotation.Router;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;

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

            return true;
        }
        return false;
    }


    public void looger(String msg) {

        System.out.println("------" + msg);
    }
}

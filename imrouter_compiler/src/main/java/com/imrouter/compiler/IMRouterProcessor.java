package com.imrouter.compiler;

import com.imrouter.annotation.Router;
import com.imrouter.annotation.RouterParam;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

/**
 * User: maodayu
 * Date: 2019/8/29
 * Time: 21:01
 */
public class IMRouterProcessor extends AbstractProcessor {

    private Elements elementUtils;
    private Filer    mFiler;
    private Types    typeUtils;

    private Map<String, RouterParam> paramMap = new HashMap<>();

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        elementUtils = processingEnvironment.getElementUtils();
        mFiler = processingEnvironment.getFiler();
        typeUtils = processingEnvironment.getTypeUtils();
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
        String options = processingEnv.getOptions().get("IMROUTER");
        int lastIndex = options.lastIndexOf(".");
        String packageName = options.substring(0, lastIndex);
        String className = options.substring(lastIndex + 1);
        if (elements != null && elements.size() > 0) {
            analyzeElementInfo(roundEnvironment, packageName, className);
            return true;
        }
        return false;
    }


    private void analyzeElementInfo(RoundEnvironment roundEnvironment, String packageName, String className) {
        paramMap.clear();
        for (Element element : roundEnvironment.getElementsAnnotatedWith(Router.class)) {
            if (element.getKind() == ElementKind.CLASS) {
                TypeElement typeElement = (TypeElement) element;
                //获取注解相关信息
                Router router = element.getAnnotation(Router.class);
                //判断是否是子类型
                int isSubType = verifyTypeMirrorType(typeElement.asType());
                if (isSubType == -1) return;
                RouterParam routerParam = new RouterParam(router.path(), typeElement.getQualifiedName().toString(), isSubType);
                paramMap.put(router.path(), routerParam);
            }
        }
        generateCodes(packageName, className);
    }

    private void generateCodes(String packageName, String className) {

        ClassName string = ClassName.get(String.class);
        ClassName routerparam = ClassName.get(RouterParam.class);
        ClassName map = ClassName.get(Map.class);
        ParameterizedTypeName parameterizedTypeName = ParameterizedTypeName.get(map, string, routerparam);

        MethodSpec.Builder builder = MethodSpec.methodBuilder("loadInfo")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .addParameter(parameterizedTypeName, "paramMap");

        for (RouterParam routerParam : paramMap.values()) {
            ClassName params = ClassName.get(RouterParam.class);
            builder.addStatement("paramMap.put($S,$T.build($S,$S,$L))", routerParam.getRouterPath(),
                    params, routerParam.getRouterPath(), routerParam.getTargetSite(), routerParam.getTarget_type());
        }

        TypeSpec typeSpec = TypeSpec.classBuilder(className)
                .addModifiers(Modifier.PUBLIC)
                .addMethod(builder.build())
                .build();

        try {
            JavaFile.builder(packageName, typeSpec).build().writeTo(mFiler);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private int verifyTypeMirrorType(TypeMirror typeMirror) {

        TypeMirror activityMirror = elementUtils.getTypeElement("android.app.Activity").asType();
        TypeMirror fragmentMirror = elementUtils.getTypeElement("androidx.fragment.app.Fragment").asType();
        TypeMirror serviceMirror = elementUtils.getTypeElement("android.app.Service").asType();
        TypeMirror broadcastMirror = elementUtils.getTypeElement("android.content.BroadcastReceiver").asType();
        if (typeUtils.isSubtype(typeMirror, activityMirror)) {
            return RouterParam.TARGET_ACTIVITY;
        } else if (typeUtils.isSubtype(typeMirror, fragmentMirror)) {
            return RouterParam.TARGET_FRAGMENT;
        } else if (typeUtils.isSubtype(typeMirror, serviceMirror)) {
            return RouterParam.TARGET_SERVICE;
        } else if (typeUtils.isSubtype(typeMirror, broadcastMirror)) {
            return RouterParam.TARGET_BROADCAST;
        }
        return -1;
    }


    public void looger(String msg) {

        System.out.println("------" + msg);
    }
}

package com.imrouter.compiler;

import com.imrouter.annotation.Router;
import com.imrouter.annotation.RouterParam;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
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
import javax.sound.midi.VoiceStatus;

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
    private List<String>             pathList = new LinkedList<>();

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
        String className = options.substring(lastIndex + 1) + "$Root";
        String groupName = className + "$Group";

        if (elements != null && elements.size() > 0) {
            analyzeElementInfo(roundEnvironment, packageName, className, groupName);
            return true;
        }
        return false;
    }


    private void analyzeElementInfo(RoundEnvironment roundEnvironment, String packageName, String className, String groupName) {
        paramMap.clear();
        pathList.clear();
        for (Element element : roundEnvironment.getElementsAnnotatedWith(Router.class)) {
            if (element.getKind() == ElementKind.CLASS) {
                TypeElement typeElement = (TypeElement) element;
                //获取注解相关信息
                Router router = element.getAnnotation(Router.class);
                //判断是否是子类型
                int isSubType = verifyTypeMirrorType(typeElement.asType());
                if (isSubType == -1) return;
                RouterParam routerParam = new RouterParam(router.path(), typeElement,
                        typeElement.getQualifiedName().toString(), isSubType);
                paramMap.put(router.path(), routerParam);
                pathList.add(router.path());
            }
        }
        generateCodes(packageName, className, groupName);
    }

    private void generateCodes(String packageName, String className, String groupName) {
        TypeElement typeElement = elementUtils.getTypeElement("com.imrouter.api.logistics.IRouterGroup");
        TypeElement rootElement = elementUtils.getTypeElement("com.imrouter.api.logistics.IRouterRoot");
        TypeElement wareElement = elementUtils.getTypeElement("com.imrouter.api.logistics.WareHouse");
        ClassName string = ClassName.get(String.class);
        ClassName routerparam = ClassName.get(RouterParam.class);
        ClassName map = ClassName.get(Map.class);
        ClassName hashMap = ClassName.get(HashMap.class);
        ClassName rootName = ClassName.get(packageName, className);

        ParameterizedTypeName parameterizedTypeName = ParameterizedTypeName.get(map, string, routerparam);

        FieldSpec fieldSpec = FieldSpec.builder(parameterizedTypeName, "paramMap", Modifier.PUBLIC,
                Modifier.STATIC).initializer("new $T()", hashMap).build();

        MethodSpec.Builder builder = MethodSpec.methodBuilder("loadInfo")
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(Override.class)
                .returns(parameterizedTypeName);

        for (RouterParam routerParam : paramMap.values()) {
            ClassName params = ClassName.get(RouterParam.class);
            ClassName target = ClassName.get(routerParam.getTargetElement());
            builder.addStatement("paramMap.put($S,$T.build($S,$T.class,$S,$L,$S))", routerParam.getRouterPath(),
                    params, routerParam.getRouterPath(), target, routerParam.getTargetSite(),
                    routerParam.getTarget_type(), groupName);
        }

        builder.addStatement("return paramMap");

        TypeSpec rootType = TypeSpec.classBuilder(className)
                .addModifiers(Modifier.PUBLIC)
                .addSuperinterface(ClassName.get(rootElement))
                .addField(fieldSpec)
                .addMethod(builder.build())
                .build();

        try {
            JavaFile.builder(packageName, rootType).build().writeTo(mFiler);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //创建分组
        MethodSpec groupSpec = MethodSpec.methodBuilder("initGroup")
                .addModifiers(Modifier.PUBLIC)
                .returns(void.class)
                .addAnnotation(Override.class)
                .addStatement("$T.put($S,$S,$T.class)", wareElement, groupName,
                        packageName + "." + className, rootName)
                .build();


        MethodSpec.Builder groupPath = MethodSpec.methodBuilder("initPath")
                .addModifiers(Modifier.PUBLIC)
                .returns(void.class)
                .addAnnotation(Override.class);

        for (int i = 0; i < pathList.size(); i++) {
            groupPath.addStatement("$T.put($S,$S)", wareElement, pathList.get(i), groupName);
        }


        TypeSpec groupType = TypeSpec.classBuilder(groupName)
                .addModifiers(Modifier.PUBLIC)
                .addSuperinterface(ClassName.get(typeElement))
                .addMethod(groupSpec)
                .addMethod(groupPath.build())
                .build();

        try {
            JavaFile.builder(packageName, groupType).build().writeTo(mFiler);
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

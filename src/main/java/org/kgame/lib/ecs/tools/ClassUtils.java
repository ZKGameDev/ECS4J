package org.kgame.lib.ecs.tools;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Modifier;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class ClassUtils {
    private static final Logger logger = LogManager.getLogger(ClassUtils.class);

    /**
     * 从包中获取指定注解的class
     */
    public static Set<Class<?>> getClassByAnnotation(String packageName, Class<? extends Annotation> annoClass) {
        Set<Class<?>> targetClasses = new HashSet<>();
        try {
            Set<Class<?>> packageClasses = getClassesFromPackage(packageName);
            for (Class<?> klass : packageClasses) {
                Annotation targetAnnotation = klass.getAnnotation(annoClass);
                if (null != targetAnnotation) {
                    targetClasses.add(klass);
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            logger.warn("get class by annotation failed!", e);
        }
        return targetClasses;
    }

    /**
     * 扫描指定包中的所有类
     * @param packageName 要扫描的包名. eg:org.kgame.lib.ecs.tools
     * @return 指定包中的所有class
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static Set<Class<?>> getClassesFromPackage(String packageName) throws IOException, ClassNotFoundException {
        Set<Class<?>> classes = new HashSet<>();

        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        String path = packageName.replace('.', File.separatorChar);
        Enumeration<URL> resources = classLoader.getResources(path);
        while (resources.hasMoreElements()) {
            URL resource = resources.nextElement();
            try {
                String resourcePath = resource.toURI().getPath();
                if (resource.getProtocol().equals("jar")) {
                    classes.addAll(getClassesFromJar(resourcePath, packageName));
                } else {
                    classes.addAll(getClassesFromDirectory(resourcePath, packageName));

                    // 添加对测试目录的扫描
                    String testPath = resourcePath.replace("main/java", "test/java");
                    File testDir = new File(testPath);
                    if (testDir.exists()) {
                        classes.addAll(getClassesFromDirectory(testPath, packageName));
                    }
                }
            } catch (URISyntaxException e) {
                logger.warn("URL {} to URI failed!", resource, e);
            }
        }

        return classes;
    }

    /**
     * 从文件夹中扫描指定包中的类
     * @param directoryPath 文件夹的全路径
     * @param packageName   目标包名
     * @return  包中的所有类
     * @throws ClassNotFoundException
     */
    private static List<Class<?>> getClassesFromDirectory(String directoryPath, String packageName) throws ClassNotFoundException {
        List<Class<?>> classes = new ArrayList<Class<?>>();
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            return classes;
        }
        File[] files = directory.listFiles();
        if (null == files) {
            return classes;
        }
        for (File file : files) {
            if (file.isDirectory()) {
                classes.addAll(getClassesFromDirectory(file.getAbsolutePath(), packageName + "." + file.getName()));
            } else if (file.getName().endsWith(".class")) {
                String className = packageName + '.' + file.getName().substring(0, file.getName().length() - 6);
                Class<?> clazz = Class.forName(className);
                classes.add(clazz);
            }
        }
        return classes;
    }

    /**
     * 从jar包中扫描指定包下的类
     * @param jarPath   jar包的全路径
     * @param packageName   目标包名
     * @return 包名中的所有类
     * @throws IOException
     * @throws ClassNotFoundException
     */
    private static List<Class<?>> getClassesFromJar(String jarPath, String packageName) throws IOException, ClassNotFoundException {
        List<Class<?>> classes = new ArrayList<Class<?>>();
        JarFile jarFile = new JarFile(jarPath);
        Enumeration<JarEntry> entries = jarFile.entries();
        while (entries.hasMoreElements()) {
            JarEntry entry = entries.nextElement();
            String name = entry.getName();
            if (name.endsWith(".class") && name.startsWith(packageName.replace('.', '/'))) {
                String className = name.substring(0, name.length() - 6).replace('/', '.');
                Class<?> clazz = Class.forName(className);
                classes.add(clazz);
            }
        }
        jarFile.close();
        return classes;
    }

    public static Set<Class<?>> getClassFromParent(String scanPath, Class<?> parentClass) {
        Set<Class<?>> targetClasses = new HashSet<>();
        try {
            Set<Class<?>> packageClasses = getClassesFromPackage(scanPath);
            for (Class<?> clazz : packageClasses) {
                if (clazz.equals(parentClass)) {
                    continue;
                }
                if (parentClass.isAssignableFrom(clazz)) {
                    targetClasses.add(clazz);
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            logger.warn("get class from parent failed!", e);
        }
        return targetClasses;
    }
    public static boolean isAbstract(Class<?> clazz) {
        return Modifier.isAbstract(clazz.getModifiers());
    }


}

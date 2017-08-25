package ru.otus.dobrovolsky.frontend.servlet.template;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import ru.otus.dobrovolsky.FrontendMain;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;
import java.util.logging.Logger;

public class TemplateProcessor {
    private static final String HTML_DIR = "html/templates" + File.separator;
    private static final Logger LOGGER = Logger.getLogger(TemplateProcessor.class.getName());
    private static TemplateProcessor instance = new TemplateProcessor();
    private final Configuration configuration;

    private TemplateProcessor() {
        configuration = new Configuration();
    }

    public static TemplateProcessor getInstance() {
        return instance;
    }

    public String getPage(String filename, Map<String, Object> data) throws IOException {
        try (Writer stream = new StringWriter()) {

            configuration.setClassForTemplateLoading(FrontendMain.class, "/");
            configuration.setDefaultEncoding("UTF-8");

            Template template = configuration.getTemplate(HTML_DIR + filename);
            template.process(data, stream);
            return stream.toString();
        } catch (TemplateException e) {
            throw new IOException(e);
        }
    }
}

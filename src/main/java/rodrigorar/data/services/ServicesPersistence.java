/******************************************************************************
* Copyright 2017 Rodrigo Ramos Rosa
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*    http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*******************************************************************************/

package rodrigorar.data.services;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import rodrigorar.data.AppConfigurationsData;
import rodrigorar.data.LanguageData;
import rodrigorar.data.TaskListData;
import rodrigorar.domain.SupportedLanguages;
import rodrigorar.domain.pojos.AppConfigurations;
import rodrigorar.domain.pojos.Language;
import rodrigorar.domain.pojos.TaskList;
import rodrigorar.utils.SystemUtils;

public class ServicesPersistence {
    private static ServicesPersistence _instance;

    public static ServicesPersistence getInstance() {
        if (_instance == null) {
            _instance = new ServicesPersistence();
        }
        return _instance;
    }

    private ServicesPersistence() { /* Empty Constructor */ }

    private Element getRootElement(String filepath) {
        Element rootElement = null;

        try {
            SAXBuilder builder = new SAXBuilder();
            File xmlFile = new File(filepath);
            Document document = (Document)builder.build(xmlFile);
            rootElement = document.getRootElement();
        } catch (IOException | JDOMException exception) {
            exception.printStackTrace();
        }

        return rootElement;
    }

    private void writeToFile(Element rootElement, String filepath) {
        try {
            Document document = new Document(rootElement);
            XMLOutputter outputter = new XMLOutputter();

            outputter.setFormat(Format.getPrettyFormat());
            outputter.output(document, new FileWriter(filepath));
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public TaskList loadTaskList(String dataDirectory) {
        TaskList loadedTaskList = null;

        Element rootElement = getRootElement(dataDirectory);
        if (rootElement != null) {
            TaskListData listData = new TaskListData();
            loadedTaskList = listData.load(rootElement);
        }

        return loadedTaskList;
    }

    public void saveTaskList(String dataDirectory, TaskList taskList) {
        TaskListData listData = new TaskListData();
        writeToFile(listData.save(taskList), dataDirectory);
    }

    public AppConfigurations loadAppConfigurations() {
        AppConfigurations configs = null;

        Element rootElement = getRootElement(SystemUtils.getDefaultLinuxSettings());
        if (rootElement != null) {
            AppConfigurationsData configData = new AppConfigurationsData();
            configs = configData.load(rootElement);
        } else {
            configs = new AppConfigurations();
            configs.setBaseDirectory(SystemUtils.getDefaultLinuxDirectory());
            configs.setDataDirectory(SystemUtils.getDefaultLinuxData());
            configs.setLanguage(SystemUtils.getDefaultLanguage());
        }

        return configs;
    }

    public void saveAppConfigurations(AppConfigurations configs) {
        AppConfigurationsData configData = new AppConfigurationsData();
        writeToFile(configData.save(configs), SystemUtils.getDefaultLinuxSettings());
    }

    public Language loadLanguage(SupportedLanguages.Languages language) {
        Language languageEntity = null;

        Element rootElement = getRootElement(language.getFile());
        if (rootElement != null) {
            LanguageData languageData = new LanguageData();
            languageEntity = languageData.load(rootElement);
        }

        return languageEntity;
    }
}

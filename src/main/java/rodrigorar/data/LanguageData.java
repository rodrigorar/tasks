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

package rodrigorar.data;

import org.jdom2.Element;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

import rodrigorar.data.interfaces.IData;
import rodrigorar.utils.Constants.XMLLabels;
import rodrigorar.domain.Language;

public class LanguageData
implements
IData<Language> {

     public Element save(Language language) {
         // TODO: Implement a NotImplmented Exception
        return null;
     }

     public Language load(Element languageElement) {
         Element nameElement = languageElement.getChild(XMLLabels.NAME);
         String name = nameElement.getText().trim();

         Element simpleNameElement = languageElement.getChild(XMLLabels.SIMPLE_NAME);
         String simpleName = simpleNameElement.getText().trim();

         Element translationsElement = languageElement.getChild(XMLLabels.TRANSLATIONS);

         List<Element> translationList = translationsElement.getChildren();
         Map<String, String> translationMap = new HashMap<String, String>();
         for (Element translation : translationList) {
             Element keyElement = translation.getChild(XMLLabels.KEY);
             String key = keyElement.getText().trim();

             Element valueElement = translation.getChild(XMLLabels.VALUE);
             String value = valueElement.getText().trim();

             translationMap.put(key, value);
         }

         return new Language(name, simpleName, translationMap);
     }
}

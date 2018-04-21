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

package rodrigorar.domain.services.language;

public class FactoryServicesLanguage {
    private static FactoryServicesLanguage _instance;

    public static final FactoryServicesLanguage getInstance() {
        if (_instance == null) {
            _instance = new FactoryServicesLanguage();
        }
        return _instance;
    }

    private FactoryServicesLanguage() {
        // Empty Constructor
    }

    public ServiceTranslation getTranslationService(String key) {
        return new ServiceTranslation(key);
    }
}
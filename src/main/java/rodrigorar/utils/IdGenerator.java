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

package rodrigorar.utils;

public class IdGenerator {
    private static IdGenerator _instance;

    public static IdGenerator getInstance() {
        if (_instance == null) {
            _instance = new IdGenerator();
        }
        return _instance;
    }

    private IdGenerator() {
        // Empty Constructor
    }

    public String generateTaskId() {
        StringBuilder taskId = new StringBuilder("task-");
        long numberId = (long)(Math.random() * 1000000000);
        taskId.append(numberId);
        return taskId.toString();
    }

    public String generateTaskListId() {
        StringBuilder taskListId = new StringBuilder("tasklist-");
        long numberId = (long)(Math.random() * 1000000000);
        taskListId.append(numberId);
        return taskListId.toString();
    }
}

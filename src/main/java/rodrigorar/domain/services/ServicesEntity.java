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

package rodrigorar.domain.services;

import rodrigorar.domain.pojos.Task;
import rodrigorar.domain.pojos.TaskList;
import rodrigorar.domain.pojos.AppConfigurations;
import rodrigorar.domain.exceptions.InvalidTitleException;
import rodrigorar.data.services.ServicesPersistence;

public class ServicesEntity {
    private static ServicesEntity _instance;
    private TaskList _taskList;

    public static ServicesEntity getInstance() {
        if (_instance == null) {
            _instance = new ServicesEntity();
        }
        return _instance;
    }

    private ServicesEntity() {
        load();
    }

    public Task newTask(String title, String description) {
        Task newTask = null;

        try {
            newTask = new Task(title, description);
            _taskList.addTask(newTask);
        } catch (InvalidTitleException exception) {
            exception.printStackTrace();
        }

        return newTask;
    }

    public Task getTask(String taskId) {
        return _taskList.getTask(taskId);
    }

    public void removeTask(String taskId) {
        _taskList.removeTask(taskId);
    }

    public void removeTask(Task task) {
        _taskList.removeTask(task);
    }

    public TaskList getTaskList() {
        return _taskList;
    }

    public void save() {
        ServicesPersistence manager = ServicesPersistence.getInstance();
        manager.saveAppConfigurations(AppConfigurations.getInstance());
        manager.saveTaskList(_taskList);
    }

    public void load() {
        ServicesPersistence manager = ServicesPersistence.getInstance();
        manager.loadAppConfigurations();
        _taskList = manager.loadTaskList();
    }

}

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

package rodrigorar.ui;

import java.util.List;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JPopupMenu;
import javax.swing.JMenuItem;
import javax.swing.DefaultListModel;
import javax.swing.ListModel;
import javax.swing.BoxLayout;
import javax.swing.Box;
import javax.swing.BorderFactory;

import rodrigorar.configs.services.ServicesLanguage;
import rodrigorar.domain.Task;
import rodrigorar.domain.TaskList;
import rodrigorar.domain.interfaces.IOperationsFacade;
import rodrigorar.domain.services.ServicesFactory;
import rodrigorar.utils.Constants.Labels;

public class MainWindow
extends
JFrame {
    public static final int CLICKS = 2;
    private MainWindow _instance;
    private ServicesLanguage _servicesLanguage = ServicesLanguage.getInstance();
    private IOperationsFacade _operations;
    private JList _list;

    public MainWindow() {
        _list = new JList(new DefaultListModel());
        _instance = this;

        ServicesFactory factory = new ServicesFactory();
        _operations = factory.getOperations();
        _servicesLanguage = ServicesLanguage.getInstance();

        initUI();
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 1, 0, 5));
        panel.setMaximumSize(new Dimension(150, 3000));

        JButton newTask = new JButton(_servicesLanguage.getTranslation(Labels.NEW_TASK));
        newTask.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                TaskWindow window = new TaskWindow(_instance);
                window.setVisible(true);
            }
        });

        JButton searchTask = new JButton(_servicesLanguage.getTranslation(Labels.SEARCH));
        searchTask.setMaximumSize(new Dimension(3000, 40));
        searchTask.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                SearchWindow window = new SearchWindow(_instance);
                window.setVisible(true);
            }
        });

        JButton settings = new JButton(_servicesLanguage.getTranslation(Labels.SETTINGS));
        settings.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                SettingsWindow window = new SettingsWindow(_instance);
                window.setVisible(true);
            }
        });

        panel.add(newTask);
        panel.add(searchTask);
        panel.add(settings);

        return panel;
    }

    public JList updateList() {
        DefaultListModel<String> model = (DefaultListModel<String>)_list.getModel();
        List<String> list = _operations.getTaskNames("");

        model.clear();

        for (String task : list) {
            model.addElement(task);
        }

        return _list;
    }

    private JPanel createListPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

        JList list = updateList();
        list.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent event) {
                int index = _list.locationToIndex(event.getPoint());
                Task task =  _operations.findTask((String)_list.getModel().getElementAt(index));

                if (event.getButton() == MouseEvent.BUTTON1
                    && event.getClickCount() == CLICKS) {;
                    TaskWindow window = new TaskWindow(_instance, task);
                    window.setVisible(true);
                } else if (event.getButton() == MouseEvent.BUTTON3) {
                    JPopupMenu menu = new JPopupMenu();
                    JMenuItem deleteItem = new JMenuItem(_servicesLanguage.getTranslation(Labels.DELETE));
                    deleteItem.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent event) {
                            _operations.deleteTask(task);
                            updateList();
                        }
                    });

                    menu.add(deleteItem);
                    menu.show(list, event.getPoint().x, event.getPoint().y);
                }
            }

            @Override public void mouseExited(MouseEvent event) {}

            @Override public void mouseEntered(MouseEvent event) {}

            @Override public void mouseReleased(MouseEvent event) {}

            @Override public void mousePressed(MouseEvent event) {}
        });

        JScrollPane scrollPane = new JScrollPane(updateList());

        panel.add(scrollPane);

        return panel;
    }

    private JPanel createLayout() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panel.add(createButtonPanel());
        panel.add(Box.createRigidArea(new Dimension(10, 0)));
        panel.add(createListPanel());

        return panel;
    }

    private void windowEvents() {
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent event) {
                _operations.save();
            }
        });
    }

    private void initUI() {
        add(createLayout());
        windowEvents();
        setTitle(_servicesLanguage.getTranslation(Labels.TASK));
        setSize(800, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
}

package ru.yandex.javacourse.kanban.tasks;

import java.util.ArrayList;

public class Epic extends Task {
    private ArrayList<SubTask> subTasks = new ArrayList<>();

    public Epic(String name, String description, Integer id) {
        super(name, description, id);
    }

    /* Если делаю как сказано в замечание, то код перестает работать, т.к. мне надо этот список либо очищать, либо удалять
     строку из списка, т.е. мне на каждую операцию прописывать отдельный метод? */
    public ArrayList<SubTask> getSubTasks() {
        return subTasks;
    }

    public void setSubTasks(ArrayList<SubTask> subTasks) {
        this.subTasks = subTasks;
    }

//    public ArrayList<SubTask> getSubTasks() {
//        return new ArrayList<>(subTasks);
//    }
//
//    public void setSubTasks(ArrayList<SubTask> subTasks) {
//        this.subTasks = new ArrayList<>(subTasks);
//    }

}

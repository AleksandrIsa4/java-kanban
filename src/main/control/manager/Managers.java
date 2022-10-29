package main.control.manager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import main.control.FileBackedTasksManager;
import main.control.HTTPTaskManager;
import main.control.InMemoryHistoryManager;
import main.control.InterfaceManager.HistoryManager;
import main.control.InterfaceManager.TaskManager;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Managers {

    public static Gson getGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter());
        return gsonBuilder.create();
    }

    public static class LocalDateTimeAdapter extends TypeAdapter<LocalDateTime> {

        @Override
        public void write(final JsonWriter jsonWriter, final LocalDateTime localDate) throws IOException {
            jsonWriter.value(localDate.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        }

        @Override
        public LocalDateTime read(final JsonReader jsonReader) throws IOException {
            return LocalDateTime.parse(jsonReader.nextString(), DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        }
    }

    public static TaskManager getDefault() {
        return new HTTPTaskManager();
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }

    public static FileBackedTasksManager loadFromFile(File file) {
        FileBackedTasksManager fileBackedTasksManager = new FileBackedTasksManager();
        fileBackedTasksManager.loadFile(file.getName());
        return fileBackedTasksManager;
    }
}
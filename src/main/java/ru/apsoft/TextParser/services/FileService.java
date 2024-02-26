package ru.apsoft.TextParser.services;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.apsoft.TextParser.entities.Response;
import ru.apsoft.TextParser.entities.Section;
import ru.apsoft.TextParser.entities.StringWrapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class FileService {

    /**
     * Разбирает файл на набор строк и структуру разделов
     *
     * @param file - исходный файл
     * @return - файл в виде набора строк + структура разделов
     */
    public Response parseFile(MultipartFile file) {
        if (!Objects.equals(file.getContentType(), "text/plain")) {
            throw new IllegalArgumentException("Тип файла должен быть .txt");
        }
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            List<StringWrapper> wrapperList = new ArrayList<>();
            String line;
            while ((line = reader.readLine()) != null) {
                wrapperList.add(wrapperOfString(line));
            }
            return new Response(wrapperList.stream().map(StringWrapper::getValue).toList(), getStructure(wrapperList));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private List<Section> getStructure(List<StringWrapper> wrapperList) {
        return wrapperList.stream()
                .filter(s -> s.getLevel() != 0)
                .map(s -> new Section(s.getValue(), s.getLevel(), wrapperList.indexOf(s) + 1))
                .toList();
    }

    private StringWrapper wrapperOfString(String str) {
        int count = 0;
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == '#') {
                count++;
            } else {
                break;
            }
        }
        return new StringWrapper(str.substring(count), count);
    }

}

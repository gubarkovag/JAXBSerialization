package com.stc07.gubarkovag;

import com.stc07.gubarkovag.entities.Application;
import com.stc07.gubarkovag.entities.Book;
import com.stc07.gubarkovag.entities.User;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {

    public static void main(String[] args) {

	    User user = new User();
	    user.setId(1);
	    user.setLogin("user");
	    user.setPassword("password");
	    user.setRole(User.Role.ADMIN);

	    Book book = new Book();
	    book.setId(1);
	    book.setName("book 1");
	    book.setGenre("horror");

        Application application = new Application();
        application.setBook_id(1);
        application.setUser_id(1);
        application.setStatus(Application.Status.APPROVED);

        jaxbMarshalling(User.class, "user.dat", user);
        jaxbMarshalling(Book.class, "book.dat", book);
        jaxbMarshalling(Application.class, "application.dat", application);

        System.out.println();
        System.out.println();

        jaxbUnmarshalling(User.class, "user.dat");
        jaxbUnmarshalling(Book.class, "book.dat");
        jaxbUnmarshalling(Application.class, "application.dat");
    }

    private static void jaxbMarshalling(Class<?> cls, String fileName, Object jaxbElement) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(cls);
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            Path path = Paths.get(fileName);
            Files.deleteIfExists(path);
            Files.createFile(path);

            marshaller.marshal(jaxbElement, path.toFile());
            marshaller.marshal(jaxbElement, System.out);
        } catch (JAXBException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void jaxbUnmarshalling(Class<?> cls, String fileName) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(cls);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

            Path path = Paths.get(fileName);
            if (!Files.exists(path)) {
                return;
            }

            Object obj = unmarshaller.unmarshal(path.toFile());
            System.out.println(obj.toString());
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }
}

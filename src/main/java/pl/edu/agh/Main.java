package pl.edu.agh;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.hibernate.Transaction;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main extends Application {
    public void getPeople(ObservableEmitter<Person> observer){
        SessionService.openSession();
        Transaction transaction2 = SessionService.getSession().beginTransaction();
        SessionService.getSession().createQuery("FROM Person", Person.class)
                .stream().forEach(person -> {observer.onNext(person); System.out.println("db " + person.getName());});
        transaction2.commit();
        SessionService.closeSession();

    }

    public List<Person> getPeople(){
        List<Person> personList = new ArrayList<>();
        SessionService.openSession();
        Transaction transaction2 = SessionService.getSession().beginTransaction();
        SessionService.getSession().createQuery("FROM Person", Person.class)
                .stream().forEach(person -> {personList.add(person); System.out.println("db " + person.getName());});
        transaction2.commit();
        SessionService.closeSession();
        return personList;

    }

    public void addPerson(int i){
        SessionService.openSession();
        Person person = new Person();
        person.setName("Krzychu " + i);
        Transaction transaction = SessionService.getSession().beginTransaction();
        SessionService.getSession().save(person);
        transaction.commit();
        SessionService.closeSession();
    }

    @Override
    public void start(Stage primaryStage) {

        addPerson(1);
        addPerson(2);
        addPerson(3);
        addPerson(4);
        addPerson(5);
        addPerson(6);
        addPerson(7);

        Observable<Person> observable = Observable.create(emitter -> {
            getPeople(emitter);
        });

        observable.subscribe(person -> System.out.println("Obersrvable " + person.getName()));


        try{
            var loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/helloView.fxml"));
            BorderPane rootLayout = loader.load();
            configureStage(primaryStage, rootLayout);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void configureStage(Stage primaryStage, BorderPane rootLayout) {
        var scene = new Scene(rootLayout);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Hello");
    }
}

package curso.java.hibernate;

import curso.java.hibernate.data.EmployeeRepository;
import curso.java.hibernate.data.ScopeRepository;
import curso.java.hibernate.data.TaskRepository;
import curso.java.hibernate.data.entity.Employee;
import curso.java.hibernate.data.entity.Task;
import curso.java.hibernate.data.entity.Scope;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@SpringBootApplication
@EnableJpaAuditing
public class HibernateExampleApp implements CommandLineRunner {

  private Logger logger = LoggerFactory.getLogger(this.getClass());

  @Autowired
  EmployeeRepository repository;

  @Autowired
  ScopeRepository repositoryScope;

  @Autowired
  TaskRepository repositoryTask;


  public static void main(String[] args) {
    SpringApplication.run(HibernateExampleApp.class, args);
  }

  @Override
  public void run(String... args) throws Exception
  {
    Scope scope1 = new Scope();
    scope1.setName("Reporting");
    scope1.setDescription("Reports, views and more");

    repositoryScope.save(scope1);

    Employee emp2 = new Employee();
    emp2.setEmail("new Employee email");
    emp2.setFirstName("Bart");
    emp2.setLastName("Simpson");

    emp2.setTasks(getTasks(scope1.getId()));

    repository.save(emp2);
    scope1.setTasks(getTasks(scope1.getId()));
    repositoryScope.save(scope1);



    repository.findAll().forEach(System.out::println);
    repositoryScope.findAll().forEach(System.out::println);
  }

  private Set<Task> getTasks(int Id_Scope) {
    Set<Task> tasks = new HashSet<>();

    Optional<Task> existingTask1 = repositoryTask.findByTaskName("report generation");
    Optional<Task> existingTask2 = repositoryTask.findByTaskName("view generation");

    if (existingTask1.isEmpty()) {
      Task task1 = new Task();
      task1.setTaskName("report generation");
      task1.setTaskDescription("Daily report generation");
      task1.setId_Scope(Id_Scope);
      tasks.add(task1);
    } else {
      tasks.add(existingTask1.get());
    }

    if (existingTask2.isEmpty()) {
      Task task2 = new Task();
      task2.setTaskName("view generation");
      task2.setTaskDescription("Daily view generation");
      task2.setId_Scope(Id_Scope);
      tasks.add(task2);
    } else {
      tasks.add(existingTask2.get());
    }

    return tasks;
  }

}

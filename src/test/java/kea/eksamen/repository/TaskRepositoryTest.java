package kea.eksamen.repository;

import kea.eksamen.model.Role;
import kea.eksamen.model.Task;
import kea.eksamen.model.TaskPriority;
import kea.eksamen.model.User;
import kea.eksamen.service.TaskService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;

import static com.mysql.cj.conf.PropertyKey.logger;
import static java.sql.Types.NULL;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class TaskRepositoryTest {
    @Autowired
    private TaskService taskService;
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private UserRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger(TaskRepository.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {


    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void addTask() {
        User user = new User();
        user.setFirstName("Test");
        user.setRole(Role.EMPLOYEE);
        user = userRepository.addUser(user); // Persist user in the database

        Task task = new Task();
        task.setTitle("testTask");
        task.setPriority(TaskPriority.HIGH);
        task.setProjectId(1);
        task.setUserId(user.getId()); // Set the valid user_id

        Task addedTask = taskService.addTask(task, task.getProjectId());

        assertNotNull(addedTask);
        assertEquals("testTask", addedTask.getTitle());
        assertEquals(TaskPriority.HIGH, addedTask.getPriority());
        assertEquals(user.getId(), addedTask.getUserId());

        Task dbTask = taskRepository.findTaskById(addedTask.getId());
        assertNotNull(dbTask); // Ensure the task was found
        assertEquals("testTask", dbTask.getTitle());
        assertEquals(user.getId(), dbTask.getUserId());

    }

    @Test
    void updateTask() {
    }

    @Test
    void deleteTask() {
    }

    @Test
    void findTaskById() {
    }

    @Test
    void getAllTasks() {
    }
}
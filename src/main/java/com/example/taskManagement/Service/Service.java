package com.example.taskManagement.Service;

import com.example.taskManagement.DTO.requestDTO.TaskRequest;
import com.example.taskManagement.DTO.responseDTO.TaskResponse;
import com.example.taskManagement.Transformer.TaskTransformer;
import com.example.taskManagement.config.CustomUserDetailsService;
import com.example.taskManagement.enums.Status;
import com.example.taskManagement.model.Task;
import com.example.taskManagement.model.User;
import com.example.taskManagement.repository.TaskRepository;
import com.example.taskManagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Service
public class Service {

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    UserRepository userRepository;

    User currentUser;




    public ResponseEntity addUser(String userName, String password) {

        //1.exceptional handling
        Optional<User> temp= Optional.ofNullable(userRepository.findByUserName(userName));

        if(temp.isEmpty())
            return new ResponseEntity<>("This userName already exists",HttpStatus.BAD_REQUEST);

        //2.logic
        PasswordEncoder passwordEncoder=new BCryptPasswordEncoder();

        User user= User.builder()
                .userName(userName)
                .password(passwordEncoder.encode(password))
                .role("ROLE_EMPLOYEE")
                .build();

        userRepository.save(user);

        return new ResponseEntity<>("User Account created successfully !!!",HttpStatus.CREATED);
    }

    public ResponseEntity enableAdmin() {

        Optional<User> temp = Optional.ofNullable(userRepository.findByUserName("admin"));
        if(temp.isEmpty())
        {
            PasswordEncoder passwordEncoder=new BCryptPasswordEncoder();

            User user= User.builder()
                    .userName("admin")
                    .password(passwordEncoder.encode("admin"))
                    .role("ROLE_ADMIN")
                    .build();

            userRepository.save(user);

            return new ResponseEntity<>("Admin account has been enabled.\n(Check the project document for admin credentials)",HttpStatus.CREATED);
        }

        return new ResponseEntity<>("Admin account is already enabled.\n(Check the project document for admin credentials)",HttpStatus.FORBIDDEN);
    }

    //-------------------------------------------

    public ResponseEntity addTask(TaskRequest taskRequest) {

        //1. acquiring current user details
        currentUser= CustomUserDetailsService.getCurrentUser();

        //2.Exceptional handling
        Optional<Task> temp = Optional.ofNullable(taskRepository.findByTitleAndUserId(taskRequest.getTitle(), currentUser.getId()));

        if(temp.isPresent())
            return new ResponseEntity("There already exists a task with the same title.",HttpStatus.BAD_REQUEST);

        //3. taskReq -> task & save
        Task task= TaskTransformer.taskRequestToTask(taskRequest,currentUser.getId());

        taskRepository.save(task);

        //3.Response
        return new ResponseEntity<>("Task added successfully !!!", HttpStatus.CREATED);
    }

    //------------------------------------------

    public ResponseEntity updateTaskStatus(int taskId, String newStatus) {

        //1. acquiring current user details
        currentUser= CustomUserDetailsService.getCurrentUser();

        //2.exceptional handling
        Optional<Task> temp = currentUser.getRole().equals("ROLE_ADMIN") ? taskRepository.findById(taskId) : Optional.ofNullable(taskRepository.findByIdAndUserId(taskId, currentUser.getId()));

        if(temp.isEmpty())
            return new ResponseEntity<>("Task is not present !!!",HttpStatus.NOT_FOUND);

        Task task=temp.get();

        if(task.getStatus().toString().equals(newStatus))
            return new ResponseEntity<>("The given state is same as current state of the task.",HttpStatus.NOT_ACCEPTABLE);


        //3.logic
        task.setStatus(Status.valueOf(newStatus));
        taskRepository.save(task);

        //4.Response
        return new ResponseEntity<>("Status updated suiccessfully",HttpStatus.ACCEPTED);
    }

    public ResponseEntity updateDescription(int taskId, String description) {

        //1. acquiring current user details
        currentUser= CustomUserDetailsService.getCurrentUser();

        //2.exceptional handling
        Optional<Task> temp = currentUser.getRole().equals("ROLE_ADMIN") ? taskRepository.findById(taskId) : Optional.ofNullable(taskRepository.findByIdAndUserId(taskId, currentUser.getId()));

        if(temp.isEmpty())
            return new ResponseEntity<>("Task is not present !!!",HttpStatus.NOT_FOUND);

        Task task=temp.get();

        if(task.getDescription().equals(description))
            return new ResponseEntity<>("The given description is same as current description of the task.",HttpStatus.NOT_ACCEPTABLE);


        //3.logic
        task.setDescription(description);

        taskRepository.save(task);

        //4.Response
        return new ResponseEntity<>("Description updated successfully.",HttpStatus.ACCEPTED);

    }

    public ResponseEntity updateDueDate(int taskId, String newDate) {

        //1. acquiring current user details
        currentUser= CustomUserDetailsService.getCurrentUser();

        //2.exceptional handling
        Optional<Task> temp = currentUser.getRole().equals("ROLE_ADMIN") ? taskRepository.findById(taskId) : Optional.ofNullable(taskRepository.findByIdAndUserId(taskId, currentUser.getId()));

        if(temp.isEmpty())
            return new ResponseEntity<>("Task is not present !!!",HttpStatus.NOT_FOUND);

        Task task=temp.get();

        if(task.getDueDate().equals(newDate))
            return new ResponseEntity<>("The given date is same as current due-date of the task.",HttpStatus.NOT_ACCEPTABLE);


        //3.logic
        task.setDueDate(newDate);
        taskRepository.save(task);

        //4.Response
        return new ResponseEntity<>("Due-Date update successfully",HttpStatus.ACCEPTED);
    }

    public ResponseEntity updateTitle(int taskId, String newTitle) {
        //1. acquiring current user details
        currentUser= CustomUserDetailsService.getCurrentUser();

        //2.exceptional handling
        Optional<Task> temp = currentUser.getRole().equals("ROLE_ADMIN") ? taskRepository.findById(taskId) : Optional.ofNullable(taskRepository.findByIdAndUserId(taskId, currentUser.getId()));

        if(temp.isEmpty())
            return new ResponseEntity<>("Task is not present !!!",HttpStatus.NOT_FOUND);

        //3.update & save
        Task task=temp.get();
        task.setTitle(newTitle);
        taskRepository.save(task);

        //4.Response
        return new ResponseEntity("Title updated successfully !!!",HttpStatus.ACCEPTED);
    }

    //----------------------------------------------

    public ResponseEntity getTask(int taskId) {

        //1. acquiring current user details
        currentUser= CustomUserDetailsService.getCurrentUser();

        //2.exceptional handling
        Optional<Task> temp = currentUser.getRole().equals("ROLE_ADMIN") ? taskRepository.findById(taskId) : Optional.ofNullable(taskRepository.findByIdAndUserId(taskId, currentUser.getId()));

        if(temp.isEmpty())
            return new ResponseEntity<>("Task is not present !!!",HttpStatus.NOT_FOUND);


        //3.logic
        Task task=temp.get();

        TaskResponse taskResponse= TaskTransformer.taskToTaskResponse(task);
        
        //4.Response
        return new ResponseEntity<>(taskResponse,HttpStatus.FOUND);
    }

    public ResponseEntity getAllTasks() {

        //1. acquiring current user details
        currentUser= CustomUserDetailsService.getCurrentUser();

        //2.exceptional handling
        List<Task> taskList= currentUser.getRole().equals("ROLE_ADMIN") ? taskRepository.findAll() : taskRepository.findByUserId(currentUser.getId());

        if(taskList.isEmpty())
            return new ResponseEntity<>("No task present !!!",HttpStatus.NOT_FOUND);

        //3. task -> taskResponse

        List<TaskResponse> taskResponseList=new ArrayList<>();

        for (Task task:taskList)
            taskResponseList.add( TaskTransformer.taskToTaskResponse(task) ) ;

        //4.Response
        return new ResponseEntity<>(taskResponseList,HttpStatus.FOUND);

    }

    public ResponseEntity getTasksByStatus(String status) {

        //1. acquiring current user details
        currentUser= CustomUserDetailsService.getCurrentUser();

        //2. filter & task -> taskResponse
        List<Task> taskList=(currentUser.getRole().equals("ROLE_ADMIN")) ? taskRepository.findAll() : taskRepository.findByUserId(currentUser.getId());

        List<TaskResponse> taskResponseList=new ArrayList<>();

        for (Task task:taskList)
            if(status.equals(task.getStatus().toString()))
                taskResponseList.add( TaskTransformer.taskToTaskResponse(task) );

        //3.exceptional handling
        if(taskResponseList.isEmpty())
            return new ResponseEntity<>("No task present !!!",HttpStatus.NOT_FOUND);

        //4.Response
        return new ResponseEntity<>(taskResponseList,HttpStatus.FOUND);

    }

    public ResponseEntity getTasksByTitleAscending() {

        //1. acquiring current user details
        currentUser= CustomUserDetailsService.getCurrentUser();

        //2.exceptional handling
        List<Task> taskList=taskRepository.orderByTitle();

        if(taskList.isEmpty())
            return new ResponseEntity<>("No task present !!!",HttpStatus.NOT_FOUND);

        //3. task -> taskResponse

        List<TaskResponse> taskResponseList=new ArrayList<>();

        if(currentUser.getRole().equals("ROLE_ADMIN"))
        {
            for (Task task:taskList)
                taskResponseList.add( TaskTransformer.taskToTaskResponse(task) ) ;
        }
        else
        {
            for (Task task:taskList)
                if(currentUser.getId()==task.getUserId())
                    taskResponseList.add( TaskTransformer.taskToTaskResponse(task) ) ;
        }

        //4.Response
        return new ResponseEntity<>(taskResponseList,HttpStatus.FOUND);
    }

    public ResponseEntity getTasksByTitleDescending() {

        //1. acquiring current user details
        currentUser= CustomUserDetailsService.getCurrentUser();

        //2.exceptional handling
        List<Task> taskList=taskRepository.orderByTitle();

        if(taskList.isEmpty())
            return new ResponseEntity<>("No task present !!!",HttpStatus.NOT_FOUND);

        //3. task -> taskResponse

        List<TaskResponse> taskResponseList=new ArrayList<>();


        for (int i=taskList.size()-1;i>=0;i--)
        {

            Task task = taskList.get(i);

            if(currentUser.getId()==task.getUserId()  || currentUser.getRole().equals("ROLE_ADMIN"))
                taskResponseList.add( TaskTransformer.taskToTaskResponse(task) ) ;
        }


        //4.Response
        return new ResponseEntity<>(taskResponseList,HttpStatus.FOUND);
    }

    //---------------------------------------------

    public ResponseEntity deleteTask(int taskid) {

        //1. acquiring current user details
        currentUser= CustomUserDetailsService.getCurrentUser();


        //2.exceptional handling
        Optional<Task> temp = Optional.ofNullable(taskRepository.findByIdAndUserId(taskid, currentUser.getId()));

        if( !(taskRepository.existsById(taskid) && currentUser.getRole().equals("ROLE_ADMIN") ) )
            if(temp.isEmpty())
                return new ResponseEntity<>("Task is not present !!!",HttpStatus.NOT_FOUND);


        //3.logic
        Task task=temp.get();

        taskRepository.delete(task);

        //4.Response
        return new ResponseEntity<>("The Task deleted successfully !!!",HttpStatus.ACCEPTED);
    }


}

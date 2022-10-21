package pl.akademiaqa.tests.e2e;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.assertj.core.api.Assertions;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.akademiaqa.dto.task.request.CreateTaskRequestDto;
import pl.akademiaqa.request.list.CreateListRequest;
import pl.akademiaqa.request.space.CreateSpaceRequest;
import pl.akademiaqa.request.space.DeleteSpaceRequest;
import pl.akademiaqa.request.space.UpdateSpaceRequest;
import pl.akademiaqa.request.task.CreateTaskRequest;
import pl.akademiaqa.request.task.UpdateTaskRequest;

class UpdateTaskE2eTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(UpdateTaskE2eTest.class);
    private static String spaceName = "ScenarioE2E";
    private static String listName = "Issues and troubles";
    private static String taskName = "issue";
    private static String updatedTaskName = "updated name";
    private static String updatedTaskDescription = "updated description";
    private static String taskDescription = "exploratory testing";
    private static String taskStatus = "to do";
    private String spaceId;
    private String listId;
    private String taskId;

    @Test
    void updateTaskE2ETest() {
        spaceId = createSpaceStep();
        LOGGER.info("Space created with id: {}", spaceId);
        updateSpaceStep();
        listId = createListStep();
        LOGGER.info("List created with id: {}", listId);
        taskId = createTaskStep();
        LOGGER.info("Task created with id: {}", taskId);
        updateTaskStep();
        closeTaskStep();
        deleteSpaceStep();
    }

    private String createSpaceStep() {
        JSONObject json = new JSONObject();
        json.put("name", spaceName);
        final Response response = CreateSpaceRequest.createSpace(json);
        Assertions.assertThat(response.statusCode()).isEqualTo(200);
        JsonPath jsonData = response.jsonPath();
        Assertions.assertThat(jsonData.getString("name")).isEqualTo(spaceName);
        return jsonData.getString("id");
    }

    private void updateSpaceStep(){
        JSONObject json = new JSONObject();
        json.put("name", "updatedSpaceName12");
        Response response = UpdateSpaceRequest.updateSpace(json, spaceId);
        Assertions.assertThat(response.statusCode()).isEqualTo(200);
        JsonPath jsonData = response.jsonPath();
        Assertions.assertThat(jsonData.getString("name")).isEqualTo("updatedSpaceName12");
    }

    private String createListStep() {
        JSONObject json = new JSONObject();
        json.put("name", listName);

        Response response = CreateListRequest.createList(json, spaceId);
        Assertions.assertThat(response.statusCode()).isEqualTo(200);

        JsonPath jsonData = response.jsonPath();
        Assertions.assertThat(jsonData.getString("name")).isEqualTo(listName);
        return jsonData.getString("id");
    }

    private String createTaskStep() {
        CreateTaskRequestDto taskDto = new CreateTaskRequestDto();
        taskDto.setName(taskName);
        taskDto.setDescription(taskDescription);
        taskDto.setStatus(taskStatus);

        final var response = CreateTaskRequest.createTask(taskDto, listId);
        LOGGER.info("CREATE TASK RESPONSE OBJECT: {}", response);
        Assertions.assertThat(response.getName()).isEqualTo(taskName);
        Assertions.assertThat(response.getDescription()).isEqualTo(taskDescription);
        return response.getId();
    }

    private void updateTaskStep() {
        JSONObject updateTask = new JSONObject();
        updateTask.put("name", updatedTaskName);
        updateTask.put("description", updatedTaskDescription);

        final var response = UpdateTaskRequest.updateTask(updateTask, taskId);
        Assertions.assertThat(response.statusCode()).isEqualTo(200);

        JsonPath json = response.jsonPath();
        Assertions.assertThat(json.getString("name")).isEqualTo(updatedTaskName);
        Assertions.assertThat(json.getString("description")).isEqualTo(updatedTaskDescription);
    }

    private void closeTaskStep() {
        JSONObject closeTask = new JSONObject();
        closeTask.put("status", "complete");

        final var response = UpdateTaskRequest.updateTask(closeTask, taskId);
        Assertions.assertThat(response.statusCode()).isEqualTo(200);

        JsonPath json = response.jsonPath();
        Assertions.assertThat(json.getString("status.status")).isEqualTo("complete");
    }

    private void deleteSpaceStep() {
        Response response = DeleteSpaceRequest.deleteSpace(spaceId);
        Assertions.assertThat(response.statusCode()).isEqualTo(200);
    }
}
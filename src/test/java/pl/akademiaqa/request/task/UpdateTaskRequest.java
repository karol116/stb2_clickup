package pl.akademiaqa.request.task;


import io.restassured.response.Response;
import org.json.JSONObject;
import pl.akademiaqa.dto.task.request.UpdateTaskRequestDto;
import pl.akademiaqa.request.BaseRequest;
import pl.akademiaqa.url.ClickUpUrl;

import static io.restassured.RestAssured.given;

public class UpdateTaskRequest {

    public static Response updateTask(JSONObject task, String taskId) {
        return given()
                .spec(BaseRequest.requestSpecWithLogs())
                .body(task.toString())
                .when()
                .put(ClickUpUrl.getTaskUrl(taskId))
                .then()
                .log().ifError()
                .extract()
                .response();
    }

    public static Response updateTask(UpdateTaskRequestDto taskDto, String taskId) {
        return given()
                .spec(BaseRequest.requestSpecWithLogs())
                .body(taskDto)
                .when()
                .put(ClickUpUrl.getTaskUrl(taskId))
                .then()
                .statusCode(200)
                .log().ifError()
                .extract()
                .response();
    }
}

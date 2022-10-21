package pl.akademiaqa.request.space;

import io.restassured.response.Response;
import org.json.JSONObject;
import pl.akademiaqa.dto.space.request.UpdateSpaceRequestDto;
import pl.akademiaqa.dto.space.response.UpdateSpaceResponseDto;
import pl.akademiaqa.request.BaseRequest;
import pl.akademiaqa.url.ClickUpUrl;

import static io.restassured.RestAssured.given;

public class UpdateSpaceRequest {

public static Response updateSpace(JSONObject space, String spaceId) {
    return given()
            .spec(BaseRequest.requestSpecWithLogs())
            .body(space.toString())
            .when()
            .put(ClickUpUrl.getSpaceUrl(spaceId))
            .then()
            .log().ifError()
            .extract()
            .response();
}

    public static UpdateSpaceResponseDto updateSpace(UpdateSpaceRequestDto spaceDto, String spaceId) {
        return given()
                .spec(BaseRequest.requestSpecWithLogs())
                .body(spaceDto)
                .when()
                .put(ClickUpUrl.getSpaceUrl(spaceId))
                .then()
                .statusCode(200)
                .log().ifError()
                .extract()
                .response()
                .as(UpdateSpaceResponseDto.class);
    }



}

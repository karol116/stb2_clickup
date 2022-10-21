package pl.akademiaqa.tests.space;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.assertj.core.api.Assertions;
import org.json.JSONObject;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import pl.akademiaqa.request.space.CreateSpaceRequest;
import pl.akademiaqa.request.space.DeleteSpaceRequest;
import pl.akademiaqa.request.space.UpdateSpaceRequest;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CreateSpaceTest {
    private static final String SPACE_NAME = "my space from Java";
    private static String spaceId;


    @Test
    @Order(1)
    void createSpaceTest() {

        JSONObject space = new JSONObject();
        space.put("name", SPACE_NAME);

        Response createResponse = CreateSpaceRequest.createSpace(space);
        Assertions.assertThat(createResponse.statusCode()).isEqualTo(200);
        JsonPath json = createResponse.jsonPath();
        Assertions.assertThat(json.getString("name")).isEqualTo(SPACE_NAME);

        spaceId = json.getString("id");
    }

    @Test
    @Order(2)
    void updateSpaceStep() {
        JSONObject json = new JSONObject();
        json.put("name", "updatedSpaceName12");
        Response response = UpdateSpaceRequest.updateSpace(json, spaceId);
        Assertions.assertThat(response.statusCode()).isEqualTo(200);
        JsonPath jsonData = response.jsonPath();
        Assertions.assertThat(jsonData.getString("name")).isEqualTo("updatedSpaceName12");

        Response deleteResponse = DeleteSpaceRequest.deleteSpace(spaceId);
        Assertions.assertThat(deleteResponse.statusCode()).isEqualTo(200);
    }
}
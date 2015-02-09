package com.recypapp.recypapp.Comunications;

import com.recypapp.recypapp.Comunications.data.ListReceta;
import com.recypapp.recypapp.Comunications.data.ListTag;
import com.recypapp.recypapp.Comunications.data.Receta;
import com.recypapp.recypapp.Comunications.data.UserLoggin;
import com.recypapp.recypapp.Comunications.data.Usuario;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * Created by silt on 7/02/15.
 */
public class Comunication {
    private final static String common_path = "/Recypapp/rest/";
    private final static String tag_path = "tag/";
    private final static String usuario_path = "usuario/";
    private final static String receta_path = "receta/";
    private final static String retrieve_path = "retrieve/";
    private final static String retrieveByEmail_path = "retrieveByEmail/";
    private final static String retrieveByUser_path = "retrieveByUser/";
    private final static String query_path = "query";
    private final static String insert_path = "insert/";
    private final static String update_path = "update/";
    private final static String delete_path = "delete/";

    /******************************************************************************************
     *
     * USUARIO
     *
    ******************************************************************************************/
    public static Usuario GetUserByEmail(String base_url, UserLoggin loggin) throws Exception {
        MediaType mediaType = MediaType.APPLICATION_JSON;
        HttpHeaders requestHeaders=new HttpHeaders();

        requestHeaders.setContentType(mediaType);

        HttpEntity<UserLoggin> requestEntity = new HttpEntity<UserLoggin>(loggin, requestHeaders);
        RestTemplate restTemplate = new RestTemplate();

        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

        ResponseEntity<Usuario> response = restTemplate.exchange(base_url + common_path
                        + usuario_path + retrieveByEmail_path, HttpMethod.POST,requestEntity,
                Usuario.class);

        return response.getBody();
    }

    public static boolean InsertUser(String base_url, Usuario usuario) throws Exception {
        MediaType mediaType = MediaType.APPLICATION_JSON;
        HttpHeaders requestHeaders=new HttpHeaders();

        requestHeaders.setContentType(mediaType);

        HttpEntity<Usuario> requestEntity = new HttpEntity<Usuario>(usuario, requestHeaders);
        RestTemplate restTemplate = new RestTemplate();

        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

        ResponseEntity<MensajeRespuesta> response = restTemplate.exchange(base_url + common_path
                + usuario_path + insert_path, HttpMethod.POST,requestEntity,
                MensajeRespuesta.class);

        return response.getStatusCode() == HttpStatus.OK;
    }

    public static boolean UpdateUser(String base_url, Usuario usuario, String pass) throws Exception {
        MediaType mediaType = MediaType.APPLICATION_JSON;
        HttpHeaders requestHeaders=new HttpHeaders();

        requestHeaders.setContentType(mediaType);
        requestHeaders.set("pass", pass);

        HttpEntity<Usuario> requestEntity = new HttpEntity<Usuario>(usuario, requestHeaders);
        RestTemplate restTemplate = new RestTemplate();

        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

        ResponseEntity<MensajeRespuesta> response = restTemplate.exchange(base_url + common_path
                + usuario_path + update_path, HttpMethod.POST, requestEntity,
                MensajeRespuesta.class);

        return response.getStatusCode() == HttpStatus.OK;
    }

    public static boolean DeleteUser(String base_url, long id) throws Exception {
        MediaType mediaType = MediaType.APPLICATION_JSON;
        HttpHeaders requestHeaders=new HttpHeaders();

        requestHeaders.setContentType(mediaType);

        HttpEntity<Long> requestEntity = new HttpEntity<Long>(id, requestHeaders);
        RestTemplate restTemplate = new RestTemplate();

        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

        ResponseEntity<MensajeRespuesta> response = restTemplate.exchange(base_url + common_path
                + usuario_path + delete_path, HttpMethod.POST,requestEntity,
                MensajeRespuesta.class);

        return response.getStatusCode() == HttpStatus.OK;
    }

    /******************************************************************************************
     *
     * RECETA
     *
     ******************************************************************************************/

    public static boolean InsertReceta(String base_url, Receta receta) throws Exception {
        MediaType mediaType = MediaType.APPLICATION_JSON;
        HttpHeaders requestHeaders=new HttpHeaders();

        requestHeaders.setContentType(mediaType);

        HttpEntity<Receta> requestEntity = new HttpEntity<Receta>(receta, requestHeaders);
        RestTemplate restTemplate = new RestTemplate();

        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

        ResponseEntity<MensajeRespuesta> response = restTemplate.exchange(base_url + common_path
                        + receta_path + insert_path, HttpMethod.POST,requestEntity,
                MensajeRespuesta.class);

        return response.getStatusCode() == HttpStatus.OK;
    }

    public static boolean UpdateReceta(String base_url, Receta receta, String pass) throws Exception {
        MediaType mediaType = MediaType.APPLICATION_JSON;
        HttpHeaders requestHeaders=new HttpHeaders();

        requestHeaders.setContentType(mediaType);
        requestHeaders.set("pass", pass);

        HttpEntity<Receta> requestEntity = new HttpEntity<Receta>(receta, requestHeaders);
        RestTemplate restTemplate = new RestTemplate();

        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

        ResponseEntity<MensajeRespuesta> response = restTemplate.exchange(base_url + common_path
                        + receta_path + update_path, HttpMethod.POST,requestEntity,
                MensajeRespuesta.class);

        return response.getStatusCode() == HttpStatus.OK;
    }

    public static boolean DeleteReceta(String base_url, long id) throws Exception {
        MediaType mediaType = MediaType.APPLICATION_JSON;
        HttpHeaders requestHeaders=new HttpHeaders();

        requestHeaders.setContentType(mediaType);

        HttpEntity<Long> requestEntity = new HttpEntity<Long>(id, requestHeaders);
        RestTemplate restTemplate = new RestTemplate();

        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

        ResponseEntity<MensajeRespuesta> response = restTemplate.exchange(base_url + common_path
                        + receta_path + delete_path, HttpMethod.POST,requestEntity,
                MensajeRespuesta.class);

        return response.getStatusCode() == HttpStatus.OK;
    }

    public static ListReceta GetRecetas(String base_url){;
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

        ListReceta recetas = restTemplate.getForObject(base_url + common_path + receta_path +
                        retrieve_path, ListReceta.class);

        return recetas;
    }

    public static ListReceta GetRecetasUsuario(String base_url, long id){;
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

        ListReceta recetas = restTemplate.getForObject(base_url + common_path + receta_path +
                retrieveByUser_path + Long.toString(id), ListReceta.class);

        return recetas;
    }

    public static ListReceta GetRecetasQuery(String base_url, long idTag, long idUser, String name,
                                             int comensales, int min_d, int max_d){;
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(base_url + common_path +
                receta_path + query_path)
                .queryParam("idTag", idTag)
                .queryParam("idUser", idUser)
                .queryParam("name", name)
                .queryParam("comensales", comensales)
                .queryParam("min_d", min_d)
                .queryParam("max_d", max_d);

        ListReceta recetas = restTemplate.getForObject(builder.build().toUri().toString(),
                ListReceta.class);

        return recetas;
    }

    public static Receta GetReceta(String base_url, long id){;
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

        Receta receta = restTemplate.getForObject(base_url + common_path + receta_path +
                retrieve_path + Long.toString(id), Receta.class);

        return receta;
    }

    /******************************************************************************************
     *
     * TAG
     *
     ******************************************************************************************/
    public static ListTag GetTags(String base_url){;
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

        ListTag tags = restTemplate.getForObject(base_url + common_path + tag_path + retrieve_path,
                ListTag.class);

        return tags;
    }
}

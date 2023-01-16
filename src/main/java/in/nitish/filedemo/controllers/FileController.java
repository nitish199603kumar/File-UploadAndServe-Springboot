package in.nitish.filedemo.controllers;

import java.awt.PageAttributes.MediaType;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import in.nitish.filedemo.payload.FileResponse;
import in.nitish.filedemo.service.FileService;

@RestController
@RequestMapping("/file")
public class FileController {

	@Autowired
	private FileService fileService;

	@Value("${project.image}")
	private String path;

	@PostMapping("/upload")
	public ResponseEntity<FileResponse> fileUpload(

			@RequestParam("image") MultipartFile image) {

		String uploadImage=null;
		try {
			uploadImage = this.fileService.uploadImage(path, image);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseEntity<FileResponse>(new FileResponse(null, "Image is not uploaded due to error on server !!"),HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<FileResponse>(new FileResponse(uploadImage, "Image is successfully Upload"),
				HttpStatus.OK);

	}
	
	//Method to serve file
	@GetMapping("/images/{imageName}")
	public void downloadImage(@PathVariable String imageName,HttpServletResponse response) throws IOException
	{
		InputStream resource = this.fileService.getResource(path, imageName);
		response.setContentType(org.springframework.http.MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(resource, response.getOutputStream());
	}

}

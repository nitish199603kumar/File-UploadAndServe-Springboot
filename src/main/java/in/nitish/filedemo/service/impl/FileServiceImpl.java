package in.nitish.filedemo.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import in.nitish.filedemo.service.FileService;

@Service
public class FileServiceImpl implements FileService {

	@Override
	public String uploadImage(String path, MultipartFile file) throws IOException {
		
		//Find out the actual file name like- abc.png, abc1.jpeg  
		String originalFilename = file.getOriginalFilename();
		
		String randomId=UUID.randomUUID().toString();
		String randomFileName = randomId.concat(originalFilename.substring(originalFilename.lastIndexOf(".")));
		
		
		//creating our full path
		String filePath=path+File.separator+randomFileName;
		
		//create folder if not present
		File f=new File(path);
		if(!f.exists())
		{
			f.mkdir();
		}
		
		//File Copy
		Files.copy(file.getInputStream(), Paths.get(filePath));
		
		
		return originalFilename;
	}

	@Override
	public InputStream getResource(String path, String file) throws FileNotFoundException {
		String fullPath=path+File.separator +file;
		InputStream iStream=new FileInputStream(fullPath);
		return iStream;
	}

}

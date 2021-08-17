package com.cinema.minute.Service;

import com.cinema.minute.Data.Entity.UploadFile;
import com.cinema.minute.Data.Entity.Videodkika;
import com.cinema.minute.Data.Repository.FileRepo;
import com.cinema.minute.Data.Repository.VideoDkikaRepo;
import com.cinema.minute.Service.UploadFile.FileInDB;
import com.cinema.minute.Service.UploadFile.StorageService;
import com.cinema.minute.ui.Model.Request.VideoDkikaRequest;
import com.cinema.minute.ui.Model.Response.videoDkikaResposne;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VideoDkikaService {
    @Autowired
    VideoDkikaRepo videoDkikaRepo;

    @Autowired
    private FileInDB fileInDB;

    @Autowired
    private FileRepo fileRepo;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    private StorageService storageService;

    public List<?> getAll() {
        List<Videodkika> videodkikaList =videoDkikaRepo.findAll();
        List<videoDkikaResposne> videoDkikaResposneList  = new LinkedList<>();
        for (int i = 0; i < videodkikaList.size()  ; i++) {
            videoDkikaResposne video = modelMapper.map(videodkikaList.get(i),videoDkikaResposne.class);
            if(videodkikaList.get(i).getVideo() != null)
            video.setIdVideo(videodkikaList.get(i).getVideo().getId());
            videoDkikaResposneList.add(video);
        }
        return videoDkikaResposneList;
    }
  // @PreAuthorize
    public videoDkikaResposne get(Integer id ) {
        Videodkika v= videoDkikaRepo.findById(id).orElseThrow(()-> new RuntimeException("this video does not exist"));
        videoDkikaResposne videoDkikaResposne = modelMapper.map(v,videoDkikaResposne.class);
       videoDkikaResposne.setIdVideo(v.getVideo().getId());
       return videoDkikaResposne;
    }


    public void addVideo(VideoDkikaRequest Videodkika) {
        Videodkika tt =modelMapper.map(Videodkika,Videodkika.class);
        // get video Uploader using fileInBD service
        UploadFile fileVideo =fileInDB.getFileById(Videodkika.getVideo());
         tt.setVideo(fileVideo);
        videoDkikaRepo.saveAndFlush(tt);
    }


    public void removeVideo(Integer id ){

        Videodkika v  = videoDkikaRepo.findById(id)
                .orElseThrow(()-> new RuntimeException("video id of video description does not exist"));
                if(v != null && v.getVideo() == null)
                    throw new NullPointerException("this video does not exist in data base ");
                videoDkikaRepo.deleteById(id);
                storageService.deleteById(v.getVideo().getId());

    }

    public void updateVideo(VideoDkikaRequest videoDkikaRequest , Integer id){
        Videodkika v =videoDkikaRepo.findById(id).orElseThrow(()-> new RuntimeException(" this video does not exist"));
        Videodkika vv = modelMapper.map(videoDkikaRequest,Videodkika.class);
        vv.setId(id);
        videoDkikaRepo.saveAndFlush(vv);
    }

    public Path getPath(Integer id){
        String p = videoDkikaRepo.findById(id).get().getVideo().getUrlFile();
       return Paths.get(p);
    }


    public File getResource(Integer id) {
       UploadFile v =  fileRepo.findById(id).orElseThrow(()-> new RuntimeException("this file does not exist in data base"));
       String url = v.getUrlFile();
       File file = new File(url);

       if(!file.exists())
           throw new RuntimeException("this file does not exist in folder ");
       return file;
    }

    public Integer uploadVideo(MultipartFile file) {
        UploadFile video =storageService.save(file);
        if(video == null)
            throw new NullPointerException("error was eccured in aved video in database or folder");
        return video.getId();
    }
}

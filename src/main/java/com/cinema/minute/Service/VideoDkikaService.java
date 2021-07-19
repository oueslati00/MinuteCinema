package com.cinema.minute.Service;

import com.cinema.minute.Data.Entity.Videodkika;
import com.cinema.minute.Data.Repository.VideoDkikaRepo;
import com.cinema.minute.Service.UploadFile.StorageService;
import com.cinema.minute.ui.Model.Request.VideoDkikaRequest;
import com.cinema.minute.ui.Model.Response.videoDkikaResposne;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VideoDkikaService {
    @Autowired
    VideoDkikaRepo videoDkikaRepo;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    private StorageService storageService;

    public List<?> getAll() {
        List<Videodkika> videodkikaList =videoDkikaRepo.findAll();
        List<videoDkikaResposne> videoDkikaResposneList  = new LinkedList<>();
        for (int i = 0; i < videodkikaList.size()  ; i++) {
            videoDkikaResposne video = modelMapper.map(videodkikaList.get(i),videoDkikaResposne.class);
            Resource file= storageService.load(videodkikaList.get(i).getVideo().getName());
            video.setFileInfo(file);
            videoDkikaResposneList.add(video);
        }
        return videoDkikaResposneList;
    }
  // @PreAuthorize
    public videoDkikaResposne get(Integer id ) {
        Videodkika v= videoDkikaRepo.findById(id).orElseThrow(()-> new RuntimeException("this video does not exist"));
        return modelMapper.map(v,videoDkikaResposne.class);
    }


    public void addVideo(VideoDkikaRequest Videodkika) {
        Videodkika tt =modelMapper.map(Videodkika,Videodkika.class);

        videoDkikaRepo.saveAndFlush(tt);
    }


    public void removeVideo(Integer id ){

        // get file id
        Integer idFile = videoDkikaRepo.findById(id).get().getVideo().getId();
        storageService.deleteById(idFile);
        videoDkikaRepo.deleteById(id);
    }

    public void updateVideo(VideoDkikaRequest videoDkikaRequest , Integer id){
        Videodkika v =videoDkikaRepo.findById(id).orElseThrow(()-> new RuntimeException(" this video does not exist"));
        Videodkika vv = modelMapper.map(videoDkikaRequest,Videodkika.class);
        vv.setId(id);
        videoDkikaRepo.saveAndFlush(vv);
    }
}

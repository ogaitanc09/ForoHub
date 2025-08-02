package com.alurachallenges.foro_hub.services;

import com.alurachallenges.foro_hub.dto.topic.TopicData;
import com.alurachallenges.foro_hub.infra.exceptions.IntegrityValidation;
import com.alurachallenges.foro_hub.models.Topic;
import com.alurachallenges.foro_hub.repositories.TopicRepository;
import com.alurachallenges.foro_hub.repositories.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class TopicService
{
    @Autowired
    UserRepository userRepository;

    @Autowired
    TopicRepository topicRepository;

    // Guarda un topico
    public TopicData publish(
            @RequestBody
            @Valid
            TopicData topicData
            )
    {
        if(!userRepository.findById(topicData.usuario()).isPresent())
        {
            throw new IntegrityValidation("Este usuario no existe");
        }

        var user = userRepository.findById(topicData.usuario()).get();
        Topic topic;
        topic = new Topic(
                null,
                user,
                topicData.curso(),
                topicData.titulo(),
                topicData.mensaje(),
                topicData.fecha(),
                true
        );

        topicRepository.save(topic);
        return new TopicData(topic);
    }

}

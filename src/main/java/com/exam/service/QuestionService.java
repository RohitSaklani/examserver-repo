package com.exam.service;

import com.exam.DTO.AddQuestionDTO;
import com.exam.DTO.OptionDTO;
import com.exam.DTO.QuestionDTO;
import com.exam.model.*;
import com.exam.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.regex.Pattern;

@Service
public class QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OptionsRepository optionsRepository;

    @Autowired
    private ResultRepository resultRepository;

    public List<Question> findByQuizId(Long quizId) {

        return questionRepository.findByQuizId(findQuiz(quizId).getId());
    }

    public List<QuestionDTO> getQuestionsWithOptions(Long quizId) {


        List<Question> questionsList = questionRepository.findByQuizId(findQuiz(quizId).getId());

        return questionsList.stream().map((question) -> {
            QuestionDTO questionDTO = new QuestionDTO();
            questionDTO.setId(question.getId());
            questionDTO.setValue(question.getValue());

            List<Option> optionList = optionsRepository.findOptionsByQuestionId(question.getId());

            List<OptionDTO> optionDTOList = optionList.stream().map((option) -> {
                OptionDTO optionDTO = new OptionDTO();
                optionDTO.setId(option.getId());
                optionDTO.setValue(option.getValue());
                return optionDTO;
            }).toList();


            questionDTO.setOptionList(optionDTOList);
            return questionDTO;
        }).toList();

    }


    public List<Question> getQuestionsWithOptionsForAdmin(Long quizId) {

        return questionRepository.findByQuizId(findQuiz(quizId).getId());


    }

    @Transactional
    public Question addQuestionWithOptionByQuizId(Long quizId, AddQuestionDTO submittedQuestion) {
        Quiz quiz = findQuiz(quizId);
        Question question = new Question();
        question.setQuiz(quiz);
        question.setValue(submittedQuestion.getQuestion());


       List<Option> optionList = new ArrayList<>();
        for(OptionDTO optionDTO :submittedQuestion.getOptionList() ){
            Option option = new Option();
            option.setValue(optionDTO.getValue());

           optionList.add(option);

            if(Objects.equals(optionDTO.getId(), submittedQuestion.getIsRightId())){
                option.setRight(true);
            }else{
                option.setRight(false);
            }
            optionsRepository.save(option);
        }

         question.setOptionList(optionList);
        Question savedQuestion =  questionRepository.save(question);


        return savedQuestion;
    }

    @Transactional
    public void addQuestionByFile(Long quizId, MultipartFile file)  {
        if (file.isEmpty()) {
            throw new RuntimeException("File is empty");
        }
        if (!file.getOriginalFilename().endsWith(".txt")) {
            throw new RuntimeException(
                    "Only txt files allowed"
            );
        }
        Quiz quiz = findQuiz(quizId);
        int i=0;
        try {
         String content =   new String( file.getBytes(), StandardCharsets.UTF_8);

         String[] questionBlocks = content.split("\\r?\\n\\r?\\n");

         for( i=0;i<questionBlocks.length;++i){
             parseAndSaveQuestion(questionBlocks[i], quiz,i);
         }


        } catch (Exception e) {
            throw new RuntimeException("invalid format at question "+(i+1)+" "+e.getMessage());
        }

    }


    public Question updateQuestionWithOptionByQuizId(Long quizId, AddQuestionDTO submittedQuestion) {
        Quiz quiz = findQuiz(quizId);
        Question question = findQuestion(submittedQuestion.getId());


        question.setValue(submittedQuestion.getQuestion());
        Question savedQuestion =  questionRepository.save(question);

        for(OptionDTO optionDTO :submittedQuestion.getOptionList() ){
            Option option = findOption(optionDTO.getId());
            option.setValue(optionDTO.getValue());
            option.setQuestion(savedQuestion);
            if(Objects.equals(optionDTO.getId(), submittedQuestion.getIsRightId())){
                option.setRight(true);
            }else{
                option.setRight(false);
            }
            optionsRepository.save(option);
        }

        return findQuestion(savedQuestion.getId());
    }

    public void deleteQuestion(Long questionId) {
        Question question = findQuestion(questionId);
         questionRepository.delete(question);
    }



    public Quiz findQuiz(Long quizId) {
        Optional<Quiz> optionalQuiz = quizRepository.findById(quizId);
        if (optionalQuiz.isPresent()) {
            return optionalQuiz.get();
        } else {
            throw new RuntimeException("Quiz not found");
        }

    }


    public Question findQuestion(Long questionId){
        Optional<Question> optionalQuestion = questionRepository.findById(questionId);

        if (optionalQuestion.isPresent()) {
            return optionalQuestion.get();
        } else {
            throw new RuntimeException("Question not found");
        }

    }

    public Option findOption(Long optionId){
        Optional<Option> optionalOption = optionsRepository.findById(optionId);

        if (optionalOption.isPresent()) {
            return optionalOption.get();
        } else {
            throw new RuntimeException("Option not found");
        }
    }


    void parseAndSaveQuestion(String questionBlock,Quiz quiz,int index) throws Exception {

        String[] lines = questionBlock.split("\\r?\\n");

        if(lines.length < 6){
            throw new Exception("Invalid Question Format");
        }

        String QuestionText = extractValue(lines[0],"Question:");

        String optionText1 = extractValue(lines[1],"A):");
        String optionText2 = extractValue(lines[2],"B):");
        String optionText3 = extractValue(lines[3],"C):");
        String optionText4 = extractValue(lines[4],"D):");
        System.out.println(optionText1+" "+optionText2+" "+optionText3+" "+optionText4+" ");

        String answer = lines[5].replace("Ans:","").trim();
        Question question = new Question();
        question.setValue(QuestionText);
        question.setQuiz(quiz);



        Option option1 = createOption(optionText1,question);
        Option option2 =createOption(optionText2,question);
        Option option3 = createOption(optionText3,question);
        Option option4 = createOption(optionText4,question);

        switch (answer.toUpperCase()) {
            case "A" -> option1.setRight(true);
            case "B" -> option2.setRight(true);
            case "C" -> option3.setRight(true);
            case "D" -> option4.setRight(true);
            default -> throw new RuntimeException(
                    "Invalid answer format: " + answer
            );
        }

        List<Option> optionList = optionsRepository.saveAll(List.of(option1,option2,option3,option4));
        question.setOptionList(optionList);
        questionRepository.save(question);


    }

    String extractValue(String line , String prefix) throws Exception {
            if(line.startsWith(prefix)){
        return  line.replaceFirst("^"+ "^" + Pattern.quote(prefix),"").trim();
            }else{
                throw new Exception("Invalid format of option "+prefix);
            }
    }

    Option createOption(String value,Question question){
        Option option= new Option();
        option.setValue(value);
        option.setQuestion(question);
        return option;
    }


}

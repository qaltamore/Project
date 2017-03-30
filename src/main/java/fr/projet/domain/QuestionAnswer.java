package fr.projet.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A QuestionAnswer.
 */
@Entity
@Table(name = "question_answer")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class QuestionAnswer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "titre")
    private String titre;

    @Column(name = "level")
    private Integer level;

    @Column(name = "question")
    private String question;

    @Column(name = "proposition_1")
    private String proposition1;

    @Column(name = "proposition_2")
    private String proposition2;

    @Column(name = "proposition_3")
    private String proposition3;

    @Column(name = "proposition_4")
    private String proposition4;

    @Column(name = "answer")
    private String answer;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public QuestionAnswer titre(String titre) {
        this.titre = titre;
        return this;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public Integer getLevel() {
        return level;
    }

    public QuestionAnswer level(Integer level) {
        this.level = level;
        return this;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getQuestion() {
        return question;
    }

    public QuestionAnswer question(String question) {
        this.question = question;
        return this;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getProposition1() {
        return proposition1;
    }

    public QuestionAnswer proposition1(String proposition1) {
        this.proposition1 = proposition1;
        return this;
    }

    public void setProposition1(String proposition1) {
        this.proposition1 = proposition1;
    }

    public String getProposition2() {
        return proposition2;
    }

    public QuestionAnswer proposition2(String proposition2) {
        this.proposition2 = proposition2;
        return this;
    }

    public void setProposition2(String proposition2) {
        this.proposition2 = proposition2;
    }

    public String getProposition3() {
        return proposition3;
    }

    public QuestionAnswer proposition3(String proposition3) {
        this.proposition3 = proposition3;
        return this;
    }

    public void setProposition3(String proposition3) {
        this.proposition3 = proposition3;
    }

    public String getProposition4() {
        return proposition4;
    }

    public QuestionAnswer proposition4(String proposition4) {
        this.proposition4 = proposition4;
        return this;
    }

    public void setProposition4(String proposition4) {
        this.proposition4 = proposition4;
    }

    public String getAnswer() {
        return answer;
    }

    public QuestionAnswer answer(String answer) {
        this.answer = answer;
        return this;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        QuestionAnswer questionAnswer = (QuestionAnswer) o;
        if (questionAnswer.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, questionAnswer.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "QuestionAnswer{" +
            "id=" + id +
            ", titre='" + titre + "'" +
            ", level='" + level + "'" +
            ", question='" + question + "'" +
            ", proposition1='" + proposition1 + "'" +
            ", proposition2='" + proposition2 + "'" +
            ", proposition3='" + proposition3 + "'" +
            ", proposition4='" + proposition4 + "'" +
            ", answer='" + answer + "'" +
            '}';
    }
}

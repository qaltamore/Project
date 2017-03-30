package fr.projet.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A QuestionAnswerTheme.
 */
@Entity
@Table(name = "question_answer_theme")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class QuestionAnswerTheme implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private Theme theme;

    @ManyToOne
    private QuestionAnswer questionAnswer;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Theme getTheme() {
        return theme;
    }

    public QuestionAnswerTheme theme(Theme theme) {
        this.theme = theme;
        return this;
    }

    public void setTheme(Theme theme) {
        this.theme = theme;
    }

    public QuestionAnswer getQuestionAnswer() {
        return questionAnswer;
    }

    public QuestionAnswerTheme questionAnswer(QuestionAnswer questionAnswer) {
        this.questionAnswer = questionAnswer;
        return this;
    }

    public void setQuestionAnswer(QuestionAnswer questionAnswer) {
        this.questionAnswer = questionAnswer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        QuestionAnswerTheme questionAnswerTheme = (QuestionAnswerTheme) o;
        if (questionAnswerTheme.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, questionAnswerTheme.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "QuestionAnswerTheme{" +
            "id=" + id +
            '}';
    }
}

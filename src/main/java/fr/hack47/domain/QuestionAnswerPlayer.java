package fr.hack47.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A QuestionAnswerPlayer.
 */
@Entity
@Table(name = "question_answer_player")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class QuestionAnswerPlayer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private Player player;

    @ManyToOne
    private QuestionAnswer questionAnswer;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Player getPlayer() {
        return player;
    }

    public QuestionAnswerPlayer player(Player player) {
        this.player = player;
        return this;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public QuestionAnswer getQuestionAnswer() {
        return questionAnswer;
    }

    public QuestionAnswerPlayer questionAnswer(QuestionAnswer questionAnswer) {
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
        QuestionAnswerPlayer questionAnswerPlayer = (QuestionAnswerPlayer) o;
        if (questionAnswerPlayer.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, questionAnswerPlayer.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "QuestionAnswerPlayer{" +
            "id=" + id +
            '}';
    }
}

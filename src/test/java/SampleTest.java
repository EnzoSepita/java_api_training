package fr.lernejo;

import org.assertj.core.api.Assertions;



class SampleTest {

    private final Sample sample = new Sample();

    @org.junit.jupiter.api.Test
    void fact_of_negative_numberthrows() {
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> sample.fact(-2))
            .withMessage("N should be positive");
    }

    @org.junit.jupiter.api.Test
    void fact_of_4_is_24() {
        int result = sample.fact(4);
        Assertions.assertThat(result).isEqualTo(24);
    }

    @org.junit.jupiter.api.Test
    void add_of_3_and_2_is_5() {
        int result = sample.op(Sample.Operation.ADD,3,2);
        Assertions.assertThat(result).isEqualTo(5);
    }
    @org.junit.jupiter.api.Test
    void mult_of_3_and_2_is_6() {
        int result = sample.op(Sample.Operation.MULT,3,2);
        Assertions.assertThat(result).isEqualTo(6);
    }


}

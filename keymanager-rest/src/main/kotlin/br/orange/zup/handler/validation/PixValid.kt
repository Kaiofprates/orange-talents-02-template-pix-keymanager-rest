package br.orange.zup.register

import io.micronaut.core.annotation.AnnotationValue
import io.micronaut.validation.validator.constraints.ConstraintValidator
import io.micronaut.validation.validator.constraints.ConstraintValidatorContext
import javax.validation.Constraint


@MustBeDocumented
@Target(AnnotationTarget.CLASS,AnnotationTarget.TYPE)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [PixValidator::class])
annotation class PixValid(
    val message: String = "Chave Pix inválida (\${validatedValue.type})",
)

class PixValidator: ConstraintValidator<PixValid,NewPixRequest> {
    override fun isValid(
        value: NewPixRequest?,
        annotationMetadata: AnnotationValue<PixValid>,
        context: ConstraintValidatorContext
    ): Boolean {
        if(value?.type == null){
            return false
        }

        return value.type.valida(value.key)

    }

}



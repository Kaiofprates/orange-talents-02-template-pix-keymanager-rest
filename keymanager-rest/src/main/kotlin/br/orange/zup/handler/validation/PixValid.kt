package br.orange.zup.register

import io.micronaut.core.annotation.AnnotationValue
import io.micronaut.validation.validator.constraints.ConstraintValidator
import io.micronaut.validation.validator.constraints.ConstraintValidatorContext
import io.micronaut.validation.validator.constraints.EmailValidator
import org.hibernate.validator.internal.constraintvalidators.hv.br.CNPJValidator
import org.hibernate.validator.internal.constraintvalidators.hv.br.CPFValidator
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

        if(value?.type == null ){
            return false
        }

        when(value.type){
            KeyType.RANDOM -> {
                if(!value.key.isNullOrBlank()) return false
            }
            KeyType.PHONE -> value.key?.matches("^\\+[1-9][0-9]\\d{1,14}\$".toRegex())
            KeyType.EMAIL -> {
                if(value.key.isNullOrBlank()){
                    return false
                }
                return EmailValidator().run {
                    initialize(null)
                    isValid(value.key, null)
                }
            }
            KeyType.CPF -> {
                if(value.key.isNullOrBlank()){
                    return false
                }
                if(!value.key?.matches("[0-9]+".toRegex())!!){
                    return false
                }
                return CPFValidator().run {
                    initialize(null)
                    isValid(value.key, null)
                }
            }
            KeyType.CNPJ ->{
                if(value.key.isNullOrBlank()){
                    return false
                }
                return CNPJValidator().run {
                    initialize(null)
                    isValid(value.key, null)
                }
            }
        }
        return true
    }

}



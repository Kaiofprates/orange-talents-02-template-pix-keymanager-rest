package br.orange.zup.handler.exceptions

import io.grpc.Status
import io.grpc.StatusRuntimeException

class InvalidPixException(status: Status) : StatusRuntimeException(status){}
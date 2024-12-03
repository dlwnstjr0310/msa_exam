package com.sparta.product.presentation.api.exception

open class ProductException(val error: Error) : RuntimeException()

class NotFoundProductException : ProductException(Error.NOT_FOUND_PRODUCT)

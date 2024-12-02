package com.sparta.gateway.config

//TODO: 가중치 기반 로드밸런싱 구현??
//@Configuration
//@LoadBalancerClient(name = "product-server", configuration = [LoadBalancerConfig.ProductLoadBalancerConfig::class])
//class LoadBalancerConfig {
//
//    class ProductLoadBalancerConfig {
//        @Bean
//        @LoadBalanced
//        fun serviceInstanceListSupplier(): ServiceInstanceListSupplier {
//            return WeightedServiceInstanceListSupplier("product-server")
//        }
//    }
//
//    class WeightedServiceInstanceListSupplier(
//        private val product: String
//    ) : ServiceInstanceListSupplier {
//
//        override fun getServiceId(): String {
//            return product
//        }
//
//        override fun get(): Flux<List<ServiceInstance>> {
//
//            println("로드밸런서 실행되는거임 ㅋㅋ 짜증나죽겠네")
//            val instances = mutableListOf<ServiceInstance>()
//
//            repeat(7) {
//                instances.add(
//                    DefaultServiceInstance(
//                        "product-1",
//                        serviceId,
//                        "localhost",
//                        19093,
//                        false
//                    )
//                )
//            }
//
//            repeat(3) {
//                instances.add(
//                    DefaultServiceInstance(
//                        "product-2",
//                        serviceId,
//                        "localhost",
//                        19094,
//                        false
//                    )
//                )
//            }
//
//            return Flux.just(instances)
//        }
//
//    }
//}
package com.cinexin.apps.backoffice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
@ComponentScan(
    basePackages = ["com.cinexin.backoffice", "com.cinexin.shared", "com.cinexin.apps.backoffice"],
    //includeFilters = [ComponentScan.Filter(Service::class)]
)
class BackofficeApplication



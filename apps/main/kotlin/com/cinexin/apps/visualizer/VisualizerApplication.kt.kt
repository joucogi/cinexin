package com.cinexin.apps.visualizer

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
@ComponentScan(
    basePackages = ["com.cinexin.backoffice", "com.cinexin.shared", "com.cinexin.apps.visualizer"],
    //includeFilters = [ComponentScan.Filter(Service::class)]
)
class VisualizerApplication



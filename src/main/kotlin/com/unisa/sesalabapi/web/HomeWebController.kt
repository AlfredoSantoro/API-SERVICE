package com.unisa.sesalabapi.web

import com.unisa.sesalab.ods.service.user.UserService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping

@Controller
class HomeWebController(
        private val usersService: UserService
)
{
    @Value("\${app.version}")
    private lateinit var version: String

    @Value("\${spring.jpa.properties.hibernate.default_schema}")
    private lateinit var dbSchema: String

    private val logger: Logger = LoggerFactory.getLogger(HomeWebController::class.java)

    @GetMapping("/index")
    fun index(model: Model): String
    {
        this.logger.info("### Get request /index")
        val isConnect = try
        {
            this.usersService.viewAccount(-1)
            true
        }
        catch (err: Exception)
        {
            this.logger.error("### DB2 connection failed:", err)
            false
        }
        model.addAttribute("version", this.version)
        model.addAttribute("onBDSchema", this.dbSchema)
        model.addAttribute("connectionStatus", isConnect)
        return "index"
    }

    @GetMapping("/")
    fun home(): String { return "redirect:index" }
}
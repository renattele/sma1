package com.renattele.sma1.data

import com.renattele.sma1.domain.MultiEntity
import com.renattele.sma1.domain.ProfileEntity

object ProfilesRepository {
    private val names = listOf(
        "Liam Smith",
        "Emma Johnson",
        "Noah Brown",
        "Olivia Jones",
        "James Davis",
        "Sophia Wilson",
        "William Garcia",
        "Isabella Martinez",
        "Lucas Anderson",
        "Mia Taylor",
        "Mason Thomas",
        "Charlotte Hernandez",
        "Elijah Moore",
        "Amelia Martin",
        "Alexander Jackson",
        "Harper White",
        "Benjamin Harris",
        "Evelyn Thompson",
        "Henry Lee",
        "Avery Lewis"
    )
    private val bios = listOf(
        "A creative marketing specialist with a passion for digital media and brand storytelling. Loves exploring new cultures and cuisines.",
        "A software engineer with expertise in machine learning and AI applications. Enjoys hiking and outdoor photography in free time.",
        "A dedicated elementary school teacher focused on innovative learning techniques. Enthusiastic about reading historical novels.",
        "An accomplished financial analyst who thrives on finding insights in data. Avid cyclist and environmental activist.",
        "A charismatic sales manager known for relationship building and strategic planning. Enjoys playing chess and volunteering at animal shelters.",
        "A freelance graphic designer specializing in digital illustration and branding. Passionate about travel and culinary arts.",
        "A project manager experienced in tech startups, excelling at cross-functional team collaboration. Loves classic rock and painting.",
        "An architect dedicated to sustainable design with a love for minimalist aesthetics. Enjoys weekend camping and photography.",
        "A social media strategist skilled in audience engagement and content creation. Film buff and aspiring screenplay writer.",
        "An experienced public relations officer who enjoys event planning and community outreach. Loves baking and gardening.",
        "A full-stack developer focused on web application development. Enjoys building DIY electronics and playing the guitar.",
        "A dedicated nurse with a compassionate approach to patient care. Enjoys yoga, meditation, and exploring nature trails.",
        "A research scientist in biotechnology, passionate about medical advancements. Avid rock climber and amateur chef.",
        "A data analyst known for expertise in statistical modeling and data visualization. Enjoys playing board games and reading thrillers.",
        "A writer and editor specializing in lifestyle and travel articles. Has a love for poetry and frequently attends open mic nights.",
        "A civil engineer focused on sustainable infrastructure projects. Loves sketching cityscapes and practicing martial arts.",
        "An HR specialist with a knack for employee engagement and team building. Passionate about jazz music and volunteering.",
        "A professional chef specializing in vegan cuisine with a flair for experimental dishes. Enjoys painting and ceramics.",
        "A high school science teacher with a focus on environmental science. Enthusiastic about astronomy and stargazing.",
        "An entrepreneur and startup founder with a background in e-commerce. Enjoys outdoor sports and reading sci-fi novels."
    )
    private val pictures = (0..53).map {
        "https://xsgames.co/randomusers/assets/avatars/pixel/$it.jpg"
    }

    val profiles by lazy {
        (0 until 25).map { generateProfile() }.toMutableList()
    }

    @Synchronized
    fun update(block: MutableList<ProfileEntity>.() -> Unit) {
        profiles.block()
    }
    fun generateProfile() =
        ProfileEntity(
            name = names.random(),
            pictureUrl = pictures.random(),
            bio = bios.random()
        )
}
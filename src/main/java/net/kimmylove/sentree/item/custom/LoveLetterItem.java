package net.kimmylove.sentree.item.custom;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public class LoveLetterItem extends Item {

    private static final Set<UUID> ALLOWED_UUIDS = Set.of(
            UUID.fromString("c52855ed-a8f6-4a15-89bd-394dc8a15294"),
            UUID.fromString("42090b34-c494-4587-a385-d4d506656355")
    );

    private static final List<String> MESSAGES = List.of(
            // ENGLISH (original)
            "Kimmy, you are my favorite person in every world.",
            "No matter what happens, I'm always on your team.",
            "If I could, I'd give you a hug through the screen.",
            "You make my life feel warm and bright.",
            "I love you — and I'm proud of you.",
            "You feel like home to me.",
            "I love the way you make ordinary moments feel special.",
            "You’re my safe place.",
            "I still get butterflies thinking about you.",
            "I choose you. Every day.",
            "I’m so lucky I get to love you.",
            "You make me want to be better — gently, not perfectly.",
            "When you smile, my brain goes quiet in the best way.",
            "You’re my favorite notification.",
            "If love had a sound, it would be your laugh.",
            "I hope your day is soft and kind to you.",
            "You deserve good things — and I'm one of them.",
            "I’m grateful for you, always.",
            "You make the world feel less heavy.",
            "I’m proud of how far you’ve come.",
            "I believe in you, even on the days you don’t.",
            "You’re my comfort and my adventure.",
            "I could search every biome and still pick you.",
            "I’d cross any ocean just to hear you say hi.",
            "You’re the best part of my day.",
            "I love you more than words… so I’ll keep showing you.",
            "Even when we’re quiet, I still feel close to you.",
            "You’re not alone. Not with me here.",
            "I love the future I imagine with you.",
            "I’m yours, in all the little ways that matter.",
            "You make my heart do the happy thing.",
            "You are precious to me.",
            "You’re my favorite kind of magic.",
            "I’m happy you exist.",
            "You make love feel easy.",
            "I’d hold your hand through any storm.",
            "If I had one wish, it would be more time with you.",
            "You’re my favorite person to talk to, forever.",
            "I’m grateful for every version of you.",
            "You’re the sweetest part of my life.",
            "I’m thinking about you right now.",
            "I hope you’re eating and drinking water, my love.",
            "You’re my person. That’s it. That’s the tweet.",
            "I love you more than I love diamonds (and that's saying something).",
            "You’re the reason my days feel brighter.",
            "You’re my best decision.",
            "I love you for real-real.",
            "You’re my favorite chapter.",
            "I’d pick you in every timeline.",

            // ENGLISH (MORE)
            "You make the little things feel like the big things.",
            "I’m so grateful I get to know you the way I do.",
            "You’re the calm in my chaos.",
            "You’re my favorite kind of beautiful: real.",
            "I love the way you care — it inspires me.",
            "If I’m doing anything right, it’s loving you.",
            "Your happiness matters to me. A lot.",
            "I love you in the loud days and the quiet days.",
            "You’re the best part of my future plans.",
            "You make me feel understood.",
            "I love you more than yesterday… and I really loved you yesterday.",
            "You’re the person I want to tell everything to.",
            "You make me feel safe to be myself.",
            "You’re the soft place I land.",
            "You’re my favorite thought.",
            "I’d pause the whole world just to be with you for a minute.",
            "You’re my comfort food, but in person form.",
            "You’re the reason I smile at my phone like an idiot.",
            "I love taking care of you, even in small ways.",
            "You deserve love that feels gentle. I want to give you that.",
            "I’m proud of you for showing up, even when it’s hard.",
            "I love your mind. I love your heart. I love you.",
            "If I could give you one thing, it’d be to see yourself through my eyes.",
            "You’re not a maybe. You’re my yes.",
            "You’re my favorite kind of forever.",
            "I’d choose a life with you over a life without you, always.",
            "I’m rooting for you in everything you do.",
            "You make my days feel worth it.",
            "You’re my favorite coincidence.",
            "I love you in a way that feels steady and true.",
            "I like who I am when I’m with you.",
            "You make me feel brave.",
            "You’re the best part of any plan.",
            "Even if today is messy, you are still you — and that’s perfect.",
            "You’re a masterpiece in progress, and I love every version.",
            "I’d build a whole world just to put a little house in it with you.",
            "If love was an item, I’d keep you in my hotbar forever.",
            "You’re my favorite rare drop.",
            "I want to make you laugh forever.",
            "When you’re tired, I’ve got you.",
            "You’re my favorite hello and my favorite goodnight.",
            "I’m grateful for the way you love.",
            "I love you more than I can explain, so I’ll prove it instead.",
            "You’re my soft place to rest.",
            "You are so loved. By me. Always.",

            // She's All That–inspired (MORE)
            "Our love feels like a 90s rom-com, but better — because it’s real.",
            "You’re the kind of person a whole movie would be written about.",
            "No makeover needed — you’ve always been the main character to me.",
            "If there was a prom scene, I’d still be looking for you, not the crowd.",
            "You’re my favorite plot twist: the best thing I never saw coming.",
            "Even if life tries to make it complicated, my choice stays simple: you.",
            "You make me want to be honest, brave, and soft — all at once.",
            "If we had a staircase moment, I’d just be standing there thinking: wow… that’s my person.",
            "I don’t care about the drama — I care about us.",

            // FRANÇAIS (original)
            "Je t'aime, Kimmy.",
            "T'es mon rayon de soleil.",
            "Mon coeur est à toi.",
            "Je pense à toi tout le temps.",
            "J'ai tellement hâte de te voir.",
            "T'es ma personne préférée, pour vrai.",
            "Tu me rends tellement heureux.",
            "Avec toi, je me sens chez moi.",
            "Tu comptes énormément pour moi.",
            "Je suis fier(ère) de toi.",
            "T'es belle, t'es brillante, t'es incroyable.",
            "Je t'aime plus que tu penses.",
            "Merci d'être toi.",
            "Je suis chanceux de t'avoir dans ma vie.",
            "Je t'envoie un gros câlin (pis un p'tit bec).",
            "T'es mon bonheur.",
            "Dans n'importe quel monde, c'est toi que je choisis.",
            "Je veux juste te garder proche de mon coeur.",
            "T'es mon amour.",
            "J'te promets d'être là pour toi.",
            "T'es mon p'tit miracle.",
            "J'ai le coeur plein quand je pense à toi.",
            "Je t'aime à l'infini.",
            "T'es ma maison.",
            "Je suis fou de toi (dans le bon sens).",

            // FRANÇAIS (MORE - Québec vibe)
            "Je t'aime fort.",
            "T'es mon coup de coeur.",
            "Avec toi, tout est plus doux.",
            "Je te choisis, aujourd'hui pis demain.",
            "T'es la plus belle partie de mes journées.",
            "J'suis tellement reconnaissant(e) de t'avoir.",
            "Je pense à toi, là, tout de suite.",
            "T'es mon p'tit coeur.",
            "J'te trouve tellement cute.",
            "T'es ma personne, point final.",
            "Je veux te rendre heureuse, pour vrai.",
            "Je suis là. Toujours.",
            "T'as pas idée à quel point je t'aime.",
            "Même quand c'est tough, on est une bonne équipe.",
            "Avec toi, je me sens en sécurité.",
            "Tu me donnes envie d'être meilleur(e).",
            "Je suis fier(ère) de toi, même dans les petites choses.",
            "T'es ma paix.",
            "Je veux bâtir un futur avec toi.",
            "J'te garde dans mon coeur, tout le temps.",
            "T'es ma douceur.",
            "Je t'aime plus que les mots peuvent dire.",
            "Je veux juste te serrer fort.",
            "Dans ma tête, c'est toi, tout le temps.",
            "T'es mon bonheur préféré.",

            // She's All That–inspiré (FR)
            "Si notre histoire était comme un film, toi tu serais toujours la vedette.",
            "Pas besoin de 'transformation' — t'es déjà parfaite à mes yeux.",
            "Bal ou pas bal, moi je te choisis toi."
    );


    public LoveLetterItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);

        // Block everyone except the one UUID
        if (!user.getUuid().equals(ALLOWED_UUIDS)) {
            if (!world.isClient) {
                user.sendMessage(Text.literal("This isn't for you").formatted(Formatting.RED), false);
            }
            return TypedActionResult.fail(stack);
        }

        if (!world.isClient) {
            int i = world.getRandom().nextInt(MESSAGES.size());
            user.sendMessage(Text.literal("❤ " + MESSAGES.get(i)), false);

            EquipmentSlot slot = (hand == Hand.MAIN_HAND) ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND;
            stack.damage(1, user, slot); // 50 uses total if maxDamage(50)

            user.getItemCooldownManager().set(this, 20);
        }

        return TypedActionResult.success(stack, world.isClient());
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, net.minecraft.item.tooltip.TooltipType type) {
        tooltip.add(Text.translatable("I Love You, Kimmy").formatted(Formatting.GRAY));
    }
}
import java.util.Random;

public class Main {
    public static int bossHealth = 700;
    public static int bossDamage = 50;
    public static String bossDefence;
    public static int[] heroesHealth = {270, 260, 250, 400, 300};
    public static int[] heroesDamage = {10, 15, 20, 0, 25};
    public static String[] heroesAttackType = {"Physical", "Magical", "Kinetic", "Medic", "Lucky"};
    public static int healingUnit = 80;
    public static int roundNumber = 0;
    public static Random random = new Random();

    public static void main(String[] args) {
        printStatistics();

        while (!isGameFinished()) {
            playRound();
        }
    }

    public static void chooseBossDefence() {
        Random random = new Random();
        int randomIndex = random.nextInt(heroesAttackType.length); // 0 1 2 3
        bossDefence = heroesAttackType[randomIndex];
    }

    public static void playRound() {
        roundNumber++;
        chooseBossDefence();
        bossHits();
        heroesHit();
        medicHeal();
        lucky();
        printStatistics();
    }  public static void bossHits() {
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0) {
                if (heroesHealth[i] - bossDamage < 0) {
                    heroesHealth[i] = 0;
                } else {
                    heroesHealth[i] = heroesHealth[i] - bossDamage;
                }
            }
        }
    }

    public static void heroesHit() {
        for (int i = 0; i < heroesDamage.length; i++) {
            if (heroesHealth[i] > 0 && bossHealth > 0) {
                int damage = heroesDamage[i];
                if (heroesAttackType[i] == bossDefence) {
                    Random random = new Random();
                    int coeff = random.nextInt(9) + 2; //2,3,4,5,6,7,8,9,10
                    damage = heroesDamage[i] * coeff;
                    System.out.println("Critical damage: " + damage);
                }
                if (bossHealth - damage < 0) {
                    bossHealth = 0;
                } else {
                    bossHealth = bossHealth - damage;
                }
            }
        }
    }

    public static void medicHeal() {//
        Random random = new Random();
        int chance = random.nextInt(heroesDamage.length);
        if (heroesHealth[chance] > 0 && heroesHealth[chance] < 100 && heroesHealth[3] > 0 && heroesAttackType[chance] != heroesAttackType[3]) {
            heroesHealth[chance] += healingUnit; // heroesHealth[chance] = heroesHealth[chance] + healingUnit
            System.out.println(heroesAttackType[3] + " healed " + heroesAttackType[chance]);
        }
    }
    private static void lucky() {//ура лаки работает
        int luckyIndex = 0;
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesAttackType[i].equals("Lucky")) {
                luckyIndex = i;
                break;
            }
        }
        boolean chance = random.nextBoolean();
        if (chance && heroesHealth[luckyIndex] > 0) {
            heroesHealth[luckyIndex] += bossDamage;
            System.out.println("Lucky gave a chance");
        }
    }
    public static boolean isGameFinished() {
        if (bossHealth <= 0) {
            System.out.println("Heroes won!!!");
            return true;
        }
        /*if (heroesHealth[0] <= 0 && heroesHealth[1] <= 0 && heroesHealth[2] <= 0 && heroesHealth[3] <= 0) {
            System.out.println("Boss won!!!");
            return true;
        }
        return false;*/
        boolean allHeroesDead = true;
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0) {
                allHeroesDead = false;
                break;
            }
        }  if (allHeroesDead) {
            System.out.println("Boss won!!!");
        }
        return allHeroesDead;
    }public static void printStatistics() {
        System.out.println("ROUND" + roundNumber + "--------------");
        /*StringDefence;
        if (bossDefence == null) {
            defence = "No defence";
             } else {
                defence = bossDefence;
         }*/
        System.out.println("Boss health: " + bossHealth + "  damage: " + bossDamage + "  defence: " +
                (bossDefence == null ? "NoDefence" : bossDefence));//вот тут вот было непонятно,  лучше удалить (тут инфа о боссе)
        for (int i = 0; i < heroesHealth.length; i++) {
            System.out.println(heroesAttackType[i] + "health: " + heroesHealth[i] + " damage: " + heroesDamage[i]);
        }
    }
}
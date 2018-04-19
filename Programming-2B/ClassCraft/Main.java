import java.util.*;

public class Main {
    public static void main(String[] args) {
        int medkit = 3;
        int selection;
        boolean vehicleOn = false;
        Scanner input = new Scanner(System.in);

        Unit player = new Unit("플레이어 마린",
                100,
                3,
                new Weapon("가우스 소총", 3, 6, false),
                new Position(1, 1),
                false
                );

        Unit vehicle = new Unit("지원군 배틀크루저",
                500,
                5,
                new Weapon("야마토 캐논", 10, 250, true),
                new Position(3, 10),
                true
        );

        Unit[] enemies = new Unit[] {
            new Unit("저그 저글링",
                    40,
                    1,
                    new Weapon("발톱", 1.5f, 5, false),
                    new Position(3, 5),
                    false
            ),
            new Unit("저그 히드라",
                    75,
                    1,
                    new Weapon("바늘 가시뼈", 4, 7, false),
                    new Position(9, 8),
                    false
            ),
            new Unit("저그 뮤탈리스크",
                    100,
                    2,
                    new Weapon("쐐기벌레", 5, 8, true),
                    new Position(5, 10),
                    true
            )
        };

        while (true) {
            if (player.hp < 0 || vehicle.hp < 0) {
                System.out.println("당신은 죽었습니다! 게임오버!");
                break;
            }

            for (int i=0; i<12; ++i) {
                for (int j = 0; j < 12; ++j) {
                    if (i == 0 || i == 11) {
                        System.out.print("#");
                    } else {
                        if (j == 0 || j == 11) {
                            System.out.print("#");
                        } else {
                            if (player.pos.y == i && player.pos.x == j && !vehicleOn)
                                System.out.print("P");
                            else {
                                boolean tp = true;

                                for (Unit u : enemies) {
                                    if (u.pos.y == i && u.pos.x == j && u.hp > 0) {
                                        System.out.print("*");
                                        tp = false;
                                    }
                                }

                                if (vehicle.pos.y == i && vehicle.pos.x == j) {
                                    System.out.print("B");
                                    tp = false;
                                }

                                if (tp)
                                    System.out.print(" ");
                            }
                        }
                    }
                }

                if (i==1) {
                    Unit pl = vehicleOn ? vehicle : player;

                    System.out.printf(" %s / HP %.2f / 아머 %.2f", pl.name, pl.hp, pl.armor);
                } else if (i == 3 || i == 4 || i == 5) {
                    if (enemies[i-3].hp > 0)
                        System.out.printf(" %s / HP %.2f / 아머 %.2f 위치 : %d, %d ", enemies[i-3].name, enemies[i-3].hp, enemies[i-3].armor, enemies[i-3].pos.x, enemies[i-3].pos.y, player.GetRange(enemies[i-3]));
                }
                System.out.println("");
            }


            System.out.println("선택: 1. 이동 / 2. 공격 / 3. 회복 (" + medkit + " 회 남음)");
            selection = input.nextInt();

            switch (selection) {
                case 1:
                    System.out.print("방향? 1234/좌우상하: ");
                    selection = input.nextInt();
                    switch (selection) {
                        case 1:
                            player.Move(Direction.left);
                            break;
                        case 2:
                            player.Move(Direction.right);
                            break;
                        case 3:
                            player.Move(Direction.up);
                            break;
                        case 4:
                            player.Move(Direction.down);
                            break;
                    }

                    if (player.pos.x == vehicle.pos.x && player.pos.y == vehicle.pos.y) {
                        vehicleOn = true;
                        System.out.println("지원군의 배틀크루저에 탑승했습니다!");
                    }

                    break;
                case 2:
                    System.out.println("공격 대상: ");
                    for (int i = 0; i < 3; ++i) {
                        Unit u = enemies[i];
                        System.out.printf("[%d] %s HP %.2f / 방어력 %.2f\n", i, u.name, u.hp, u.armor);
                    }

                    Unit target;
                    while (true) {
                        selection = input.nextInt();
                        target = enemies[selection];
                        if (target == null) {
                            System.out.println("잘못된 타겟! 다시 설정하세요!");
                        } else if (target.hp < 0) {
                            System.out.println("이미 죽은 타겟! 다시 설정하세요!");
                        } else {
                            break;
                        }
                    }

                    if (vehicleOn) {
                        vehicle.Attack(target);
                    } else {
                        player.Attack(target);
                    }
                    break;
                case 3:
                    medkit--;
                    player.hp = 100;
                    System.out.println("체력이 전부 회복되었습니다!");
                    break;
            }

            if (enemies[0].hp <= 0 && enemies[1].hp <= 0 && enemies[2].hp <= 0) {
                System.out.println("적을 모두 사살했습니다! 승리!");
                break;
            }

            for (Unit u : enemies) {
                if (u.hp <= 0) continue;

                if (vehicleOn) {
                    if (u.Attackable(vehicle))
                        u.Attack(vehicle);
                }
                else {
                    if (u.Attackable(player))
                        u.Attack(player);
                }
            }
        }

        input.next();
        input.close();
    }
}

package com.company;

public class Unit {
    public String name;
    public double hp;
    public double armor;
    public Weapon weapon;
    public Position pos;
    public Boolean air;

    public Unit(String _name, double _hp, double _armor, Weapon _weapon, Position _pos, Boolean _air) {
        name = _name;

        if (_hp > 0)  hp = _hp;
        else hp = 100.0f;

        armor = _armor;

        if (_weapon != null)
            weapon = _weapon;

        if (_pos == null)
            pos = new Position(1, 1);
        else
            pos = _pos;

        air = _air;
    }

    public void Move(Direction direction) {
        if (direction == Direction.up) {
            pos.y--;
        } else if (direction == Direction.down) {
            pos.y++;
        } else if (direction == Direction.left) {
            pos.x--;
        } else {
            pos.x++;
        }
    }

    public void Attack(Unit unit) {
        System.out.print(name + "이(가)" + weapon.name + "으로 " + unit.name + "을 공격:");

        if (!Attackable(unit)) {
            System.out.println("사거리 미달! 공격실패!");
            return;
        }

        double effectiveDamage = weapon.damage;
        effectiveDamage *= (100 - unit.armor) / 100;
        effectiveDamage *=  (weapon.range - GetRange(unit) + 1) / weapon.range;

        unit.hp -= effectiveDamage;
        System.out.printf("%.2f의 데미지! ", effectiveDamage);
        if (unit.hp < 0)
            System.out.println("사살!");
        else
            System.out.println("");
    }

    public boolean Attackable(Unit unit) {
        if (!weapon.airWeapon && unit.air) {
            return false;
        } else {
            return weapon.range > GetRange(unit);
        }
    }

    public double GetRange(Unit unit) {
        double xrange = pos.x > unit.pos.x ? pos.x - unit.pos.x : unit.pos.x - pos.x;
        double yrange = pos.y > unit.pos.y ? pos.y - unit.pos.y : unit.pos.y - pos.y;

        return Math.sqrt(xrange * xrange + yrange * yrange);
    }
}

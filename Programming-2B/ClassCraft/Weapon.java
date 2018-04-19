public class Weapon {
    public String name;
    public double range;
    public double damage;
    public Boolean airWeapon;

    public Weapon(String _name, double _range, double _damage, Boolean _airAttack) {
        name = _name;

        if (_range > 0)
            range = _range;
        else
            range = 1.0f;

        if (_damage > 0)
            damage = _damage;
        else
            damage = 0.0f;

        airWeapon = _airAttack;
    }
}

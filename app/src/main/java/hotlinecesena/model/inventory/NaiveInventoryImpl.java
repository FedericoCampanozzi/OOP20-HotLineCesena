package hotlinecesena.model.inventory;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import hotlinecesena.model.entities.items.Firearm;
import hotlinecesena.model.entities.items.Item;
import hotlinecesena.model.entities.items.Weapon;
import hotlinecesena.model.entities.items.Ammunition;

/**
 * 
 * Simple inventory capable of containing one item, one weapon and all kinds of ammunition.
 *
 */
public class NaiveInventoryImpl implements Inventory {
    
    private static final int NO_AMMO = 0;
    private Optional<Item> ownedItem;
    private Optional<Weapon> ownedWeapon;
    private final Map<Ammunition, Integer> ownedAmmo;
    
    public NaiveInventoryImpl() {
        this.ownedItem = Optional.empty();
        this.ownedWeapon = Optional.empty();
        this.ownedAmmo = new EnumMap<>(Ammunition.class);
    }
    
    public NaiveInventoryImpl(final Optional<Item> item, final Optional<Weapon> weapon,
            final EnumMap<Ammunition, Integer> ammo) {
        this.ownedItem = item;
        this.ownedWeapon = weapon;
        this.ownedAmmo = ammo;
    }
    
    private <I extends Item> void add(Optional<I> field, I item) {
        if (field.isPresent()) {
            this.drop(field);
        }
        field = Optional.of(item);
    }


    private <I extends Item> void drop(Optional<I> field) {
        // TODO
        field = Optional.empty();
    }


    /**
     * 
     * @unused index
     */
    @Override
    public void addItem(Item item, int index) {
        this.add(this.ownedItem, item);
    }

    /**
     * 
     * @unused index
     */
    @Override
    public void addWeapon(Weapon w, int index) {
        this.add(this.ownedWeapon, w);
        
    }

    @Override
    public void addAmmo(Ammunition ammo, int quantity) {
        this.ownedAmmo.put(ammo, this.ownedAmmo.getOrDefault(ammo, NO_AMMO) + quantity);
    }

    @Override
    public List<Optional<Item>> getOwnedItems() {
        return List.of(this.ownedItem);
    }

    @Override
    public List<Optional<Weapon>> getOwnedWeapons() {
        return List.of(this.ownedWeapon);
    }

    @Override
    public Optional<Weapon> getEquippedWeapon() {
        return this.ownedWeapon;
    }
    

    /**
     * 
     * Not implemented.
     */
    @Override
    public void setEquippedWeapon(int index) {
    }
    
    @Override
    public void reloadWeapon() {
        if (this.ownedWeapon.isPresent() && this.ownedWeapon.get() instanceof Firearm) {
            final Firearm gun = (Firearm) this.ownedWeapon.get();
            final int current = gun.getCurrentAmmo();
            final int owned = this.ownedAmmo.getOrDefault(gun.getCompatibleAmmo(), NO_AMMO);
            
            if (owned > NO_AMMO && current < gun.getMagazineSize()) {
                final int needed = gun.getMagazineSize() - current;
                
                if (owned >= needed) {
                    gun.reload(needed);
                    this.ownedAmmo.put(gun.getCompatibleAmmo(), owned - needed);
                } else {
                    gun.reload(owned);
                    this.ownedAmmo.put(gun.getCompatibleAmmo(), NO_AMMO);
                }
            }
        }
    }

    /**
     * 
     * @unused index
     */
    @Override
    public void dropItem(int index) {
        this.drop(this.ownedItem);
    }

    /**
     * 
     * @unused index
     */
    @Override
    public void dropWeapon(int index) {
        this.drop(this.ownedWeapon);
    }
}

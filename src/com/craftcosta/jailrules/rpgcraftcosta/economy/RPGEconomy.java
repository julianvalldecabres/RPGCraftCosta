/* 
 * Copyright 2016 jail.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.craftcosta.jailrules.rpgcraftcosta.economy;

/**
 *
 * @author jail
 */
public class RPGEconomy {

    //Campos de la clase
    long money;

    /**
     * Constructor de la clase RPGEconomy Crea un objeto con dinero 0
     */
    public RPGEconomy() {
        this.money = 0;
    }//cierre del constructor

    /**
     * Constructor de la clase RPGEconomy Crea un objeto con el dinero pasado
     * como parametro
     *
     * @param money define el dinero con el que se iniciará el objeto
     */
    public RPGEconomy(long money) {
        this.money = money;
    }//cierre del constructor

    /**
     * Metodo que establece el dinero del objeto RPGEconomy
     *
     * @param money define el dinero que se establecerá en el objeto
     */
    public void setMoney(long money) {
        this.money = money;
    }//cierre del metodo

    /**
     * Metodo que obtiene el dinero del objeto RPGEconomy
     *
     * @return el dinero del objeto RPGEconomy
     */
    public long getMoney() {
        return this.money;
    }//cierre del metodo

    /**
     * Metodo que resetea a 0 el dinero del objeto RPGEconomy
     */
    public void resetMoney() {
        this.money = 0;
    }

    /**
     * Metodo que substrae dinero de un objeto RPGEconomy
     *
     * @param money define el dinero a substraer del objeto RPGEconomy
     */
    public void withdrawMoney(long money) throws InsufficientFundsException, NegativeMoneyException {
        if (money < 0) {
            throw new NegativeMoneyException("El dinero no puede ser negativo");
        }
        if (money > this.money) {
            throw new InsufficientFundsException("No tienes suficiente dinero");
        } else {
            this.money -= money;
        }
    }//cierre del metodo

    /**
     * Metodo que anyade dinero a un objeto RPGEconomy
     *
     * @param money define el dinero que se añade al objeto RPGEconomy
     */
    public void addMoney(long money) throws NegativeMoneyException {
        if (money < 0) {
            throw new NegativeMoneyException("El dinero no puede ser negativo");
        }
        this.money += money;
    }//cierre del metodo
}

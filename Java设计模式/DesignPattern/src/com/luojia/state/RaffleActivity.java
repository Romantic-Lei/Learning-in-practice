package com.luojia.state;

/**
 * @Author Romantic-Lei
 * @Date 2020/6/7
 * @description
 */
public class RaffleActivity {

    // state
    State state = null;
    // 奖品数量
    int count = 0;

    // 是个属性，表示四种状态
    State noRaffleState = new NoRaffleState(this);
    State canRaffleState = new CanRaffleState(this);

    State dispenseState = new DispenseState(this);
    State dispenseOutState = new DispenseOutState(this);

    public RaffleActivity(int count) {
        this.state = getNoRaffleState();// 初始化为不能抽奖状态
        this.count = count;
    }

    public void debuctMoney() {
        state.deductMoney();
    }

    public void raffle(){
        if(state.raffle()){
            state.dispensePrize();
        }
    }


    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public int getCount() {
        int curCount = count;
        count--;
        return curCount;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public State getNoRaffleState() {
        return noRaffleState;
    }

    public void setNoRaffleState(State noRaffleState) {
        this.noRaffleState = noRaffleState;
    }

    public State getCanRaffleState() {
        return canRaffleState;
    }

    public void setCanRaffleState(State canRaffleState) {
        this.canRaffleState = canRaffleState;
    }

    public State getDispenseState() {
        return dispenseState;
    }

    public void setDispenseState(State dispenseState) {
        this.dispenseState = dispenseState;
    }

    public State getDispenseOutState() {
        return dispenseOutState;
    }

    public void setDispenseOutState(State dispenseOutState) {
        this.dispenseOutState = dispenseOutState;
    }
}

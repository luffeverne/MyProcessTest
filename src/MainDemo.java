import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainDemo {
    public void init() {
        Frame frame = new Frame("程序");
        String[] names = {"第一张图片", "第二张图片", "第三张图片"};

        //第一个容器，放三个按钮
        CardLayout cardLayout = new CardLayout();
        Panel p1 = new Panel();
        p1.setLayout(cardLayout);
        for (int i = 0; i < 3; i++) {
            p1.add(names[i], new Button(names[i]));
        }

        //第二个容器，放卡片
        Panel p2 = new Panel();

        //给按钮设置监听事件

        ActionListener listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String command = e.getActionCommand();
                switch (command) {
                    case "第一张":
                        cardLayout.show(p1, "第一张图片");
                        break;
                    case "第二张":
                        cardLayout.show(p1, "第二张图片");
                        break;
                    case "第三张":
                        cardLayout.show(p1, "第三张图片");
                        break;
                }
            }
        };

        Button b1 = new Button("第一张");
        Button b2 = new Button("第二张");
        Button b3 = new Button("第三张");

        b1.addActionListener(listener);
        b2.addActionListener(listener);
        b3.addActionListener(listener);

        p2.add(b1);
        p2.add(b2);
        p2.add(b3);


        frame.add(p1);
        frame.add(p2, BorderLayout.SOUTH);

        frame.pack();
        frame.setVisible(true);

    }

    public static void main(String[] args) {
        MainDemo demo = new MainDemo();
        demo.init();
    }
}
